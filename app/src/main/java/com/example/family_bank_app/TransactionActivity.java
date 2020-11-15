package com.example.family_bank_app;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.util.Date;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {
    private static final String TAG = TransactionActivity.class.getSimpleName();


    String note[], amount[], currentBal[];
    TextView Note, Amount, CurrentBal;
    AccountViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Log.i(TAG, "in transactivity");


        note = new String[]{"Cheese", "More Cheese", "PepperJack"};
        amount = new String[]{"5.00", "6.00", "7.00"};
        currentBal = new String[]{"20.00", "14.00", "7.00" };
        viewModel = new AccountViewModel();
        Long UID = getIntent().getLongExtra("UID", 0);

        /*
           final Observer<AccountEntity> getAccountObserver = newAccount -> {
            if (newAccount == null) {
                return;
            }

                AccountEntity account = newAccount;
                names[i] = account.getAccountName();
                balances[i] = String.valueOf(account.getAccountBalance());
                UIDS[i] = account.getAccountUid();




            myAccountAdapter.notifyDataSetChanged();

        };

        viewModel.getAccount(this, UID) {

        };
    */
        int pos = getIntent().getIntExtra("POSITION", 0);

        Note = findViewById(R.id.transactionActivityMessage);
        Amount = findViewById(R.id.transactionActivityAmt);
        CurrentBal = findViewById(R.id.transactionActivityBal);

        Note.setText(note[pos]);
        Amount.setText("Amount: $" + amount[pos]);
        CurrentBal.setText("Balance: $" + currentBal[pos]);






    }

    public void toAccountActivity(View view) {

        finish();

    }


}