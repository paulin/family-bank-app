package com.example.family_bank_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.String.valueOf;

public class AccountActivity extends AppCompatActivity implements Dialog_DepositWithdraw.DepositWithdrawDialogListener {
    RecyclerView transactionRecyclerView;
    MyTransactionAdapter myTransactionAdapter;
    List<String> transactionName, date;
    List<Double> amount, currentBal;
    List<Long> UIDS;
    String name;
    Double balance;
    Long UID;

    AccountViewModel accountViewModel;
    TransactionViewModel transactionViewModel;
    TextView accountName, accountBal;

    private static final String TAG = "AccountActivity";

    //inits for deposit and withdraw dialog
    EditText deposit_withdraw_dialog;
    Button btn_withdraw, btn_deposit;
    private int status_depositWithdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        transactionName = new ArrayList<String>();
        date = new ArrayList<String>();
        amount = new ArrayList<Double>();
        currentBal = new ArrayList<Double>();
        UIDS = new ArrayList<Long>();
        name = "";
        balance = 0.0;


        accountViewModel = new AccountViewModel();

        int pos = getIntent().getIntExtra("POSITION", 0);
        UID = getIntent().getLongExtra("UID", 0);

        accountName = findViewById(R.id.NameOfAccount);
        accountBal = findViewById(R.id.balance);

        transactionRecyclerView = findViewById(R.id.TransactionRecycler);

        myTransactionAdapter = new MyTransactionAdapter(this, transactionName , amount, currentBal, date, UIDS);
        transactionRecyclerView.setAdapter(myTransactionAdapter);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myTransactionAdapter.notifyDataSetChanged();


        final Observer<AccountEntity> getAccountObserver = Account -> {
            if (Account == null) {
                return;
            }
            name = Account.getAccountName();
            balance = Account.getAccountBalance();

            accountName.setText(name);
            accountBal.setText("Balance: $" + balance);


        };

        AccountViewModel.getAccount(this, UID).observe(this, getAccountObserver);


        final Observer<List<TransactionEntity>> getTransactionsObserver = transactionEntities -> {
            if (transactionEntities == null || transactionEntities.size() < 0) {
                return;
            }

            transactionName.clear();
            currentBal.clear();
            amount.clear();
            UIDS.clear();
            date.clear();

            for(int i=0; i < transactionEntities.size();i++) {
                TransactionEntity transaction = transactionEntities.get(i);
                transactionName.add(transaction.getTransactionTitle());
                currentBal.add(transaction.getTransactionAmount());
                amount.add(transaction.getTransactionAmount());
                UIDS.add(transaction.getTransactionUid());
                date.add(transaction.getTransactionDate());
            }

            myTransactionAdapter.notifyDataSetChanged();
        };


        TransactionViewModel.getAllTransactions(this, UID).observe(this, getTransactionsObserver);
        

        /*
        Code for deposit and withdraw dialog below:
        */
        btn_deposit = findViewById(R.id.Btn_Deposit);
        btn_deposit.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                //CLICK DEPOSIT
                status_depositWithdraw = Dialog_DepositWithdraw.STATUS_DEPOSIT;
                depositWithdrawDialog(status_depositWithdraw);
            }
        });

        btn_withdraw = findViewById(R.id.Btn_Withdraw);
        btn_withdraw.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                //CLICK WITHDRAW
                status_depositWithdraw = Dialog_DepositWithdraw.STATUS_WITHDRAW;
                depositWithdrawDialog(status_depositWithdraw);
            }
        });
    }

    //Called when either deposit or withdraw is clicked
    public void depositWithdrawDialog(int status) {
        Dialog_DepositWithdraw depwithDialog = new Dialog_DepositWithdraw();

        //Expecting argument for STATUS_TYPE of STATUS_WITHDRAW or STATUS_DEPOSIT
        Bundle args = new Bundle();
        args.putInt("STATUS_TYPE", status);
        depwithDialog.setArguments(args);

        depwithDialog.show(getSupportFragmentManager(), "deposit and withdraw dialog");
    }

    @Override
    public void sendText(double amount, String memo) {
        if (status_depositWithdraw == Dialog_DepositWithdraw.STATUS_WITHDRAW) {
            amount = amount * -1;
        }

        //Toast.makeText(getApplicationContext(), "" + amount + " " + memo, Toast.LENGTH_LONG).show();
        //send transaction to Transaction handler

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionAmount(amount);
        transactionEntity.setTransactionDate(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        transactionEntity.setTransactionTitle(memo);
        transactionEntity.setAccountMainUid(UID);

        TransactionViewModel.createTransaction(transactionRecyclerView.getContext(), transactionEntity);
        Log.i(TAG, ""+TransactionViewModel.getTransaction(transactionRecyclerView.getContext(), 0) );
    }

     /*
     // Demo to test transaction data
     TransactionEntity transactionEntity = new TransactionEntity();

     transactionEntity.setTransactionAmount(100.00);
     transactionEntity.setTransactionDate("1/1/1111");
     transactionEntity.setTransactionTitle("test transaction");
     transactionEntity.setAccountMainUid(0);

     TransactionViewModel.createTransaction(view.getContext(), transactionEntity);
     Log.i(TAG, ""+TransactionViewModel.getTransaction(view.getContext(),0));
     */
}