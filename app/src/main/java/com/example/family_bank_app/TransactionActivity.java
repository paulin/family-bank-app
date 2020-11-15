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


    String note, date;
    Double amount, currentBal;
    TextView Note, Amount, CurrentBal, Date;
    AccountViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Log.i(TAG, "in transactivity");


        note = getIntent().getStringExtra("NAME");
        amount = getIntent().getDoubleExtra("AMOUNT", 0);
        currentBal = getIntent().getDoubleExtra("BAL", 0);
        date = getIntent().getStringExtra("DATE");
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

        Note = findViewById(R.id.transactionActivityMessage);
        Amount = findViewById(R.id.transactionActivityAmt);
        CurrentBal = findViewById(R.id.transactionActivityBal);
        Date = findViewById(R.id.transactionActivityDate);

        Note.setText("Note" + note);
        Amount.setText("Amount: $" + amount);
        CurrentBal.setText("Balance: $" + currentBal);
        Date.setText(date);






    }

    public void toAccountActivity(View view) {

        finish();

    }


}