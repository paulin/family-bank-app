package com.example.family_bank_app;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Dialog_CreateAcc.CreateAccountDialogListener {
    RecyclerView accountRecyclerView;
    MyAccountAdapter myAccountAdapter;
    List<String> names;
    List<String> balances;
    List<Long> UIDS;
    ImageButton createAcct;
    AccountViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        names = new ArrayList<String>();
        balances = new ArrayList<String>();     //Need to add Array-doubler in Observer
        UIDS = new ArrayList<Long>();

        viewModel = new AccountViewModel();

        //Account Loader from Database

        final Observer<List<AccountEntity>> getAccountsObserver = newAccounts -> {
            if (newAccounts == null || newAccounts.size() <= 0) {
                return;
            }
            names.clear();
            balances.clear();
            UIDS.clear();
        for(int i=0; i < newAccounts.size();i++) {
            AccountEntity account = newAccounts.get(i);
            names.add(account.getAccountName());
            balances.add(String.valueOf(account.getAccountBalance()));
            UIDS.add(account.getAccountUid());
        }



            myAccountAdapter.notifyDataSetChanged();

        };

        viewModel.loadAllByIds(this).observe(this, getAccountsObserver);


        accountRecyclerView = findViewById(R.id.AccountRecycler);

        myAccountAdapter = new MyAccountAdapter(this, names, balances, UIDS);
        accountRecyclerView.setAdapter(myAccountAdapter);
        accountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAccountAdapter.notifyDataSetChanged();

        //Set up onclick listener for the create new account button
        createAcct = findViewById(R.id.CreateAcct);
        createAcct.setOnClickListener(v -> createAccDialog());

    }

    //Called when create new account button is clicked
    //Brings up dialog for entering account name and starting balance
    public void createAccDialog() {
        // see CreateAccDialog class in /java/com.example.family_bank_app
        Dialog_CreateAcc accDialog = new Dialog_CreateAcc();
        accDialog.show(getSupportFragmentManager(), "create acc dialog");
    }

    //From CreateAccDialog.java
    //Gets the values entered in the create account dialog
    @Override
    public void sendText(String name, double balance) {
        updateDatabase(name, balance);
    }

    //Updates the DB with new values
    public void updateDatabase(String acctName, double acctBal) {
        AccountEntity newAccount = new AccountEntity();
        newAccount.setAccountName(acctName);
        newAccount.setAccountBalance(acctBal);
        AccountViewModel.updateAccount(this, newAccount);

    }

}