package com.example.family_bank_app;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.util.Date;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {
    private static final String TAG = TransactionActivity.class.getSimpleName();


    String note, date, status, transactionStatus;
    Double amount, currentBal;
    Long UID;
    boolean deleteTransaction;
    int position;
    TextView Note, Amount, CurrentBal, Date, Status;
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
        UID = getIntent().getLongExtra("TUID", 0);
        status = getIntent().getStringExtra("STATUS");
        position = getIntent().getIntExtra("POSITION", 0);

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
        Status = findViewById(R.id.transactionActivityStatus);

        Note.setText("Note: " + note);
        Amount.setText("Amount: $" + amount);
        CurrentBal.setText("Balance: $" + currentBal);
        Date.setText(date);

        if (status.equals("ok")) {
            Status.setText("Status: Completed");
        } else {
            Status.setText("Status: Deleted");
        }

    }

    public void deleteTransaction(View view) {
//        Toast.makeText(this, "delete: " + UID, Toast.LENGTH_LONG).show();

        deleteTransaction = true;

        final Observer<TransactionEntity> getTransactionObserver = Transaction -> {
            if (Transaction == null){ return; }

            transactionStatus = Transaction.getTransactionStatus();

            if (deleteTransaction) {
                if (status.equals("ok")) {
                    Toast.makeText(this, Transaction.getTransactionUid() + "Transaction deleted", Toast.LENGTH_LONG).show();
                    Transaction.setTransactionStatus("deleted");
                    TransactionViewModel.createTransaction(this, Transaction);
                    deleteTransaction = false;
                    Intent intent = new Intent(TransactionActivity.this, TransactionActivity.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("UID", UID); //@@@ look into errors with deleting. Also consider how to change account balance in transaction.
                finish();
                startActivity(intent);

                } else {
                    Toast.makeText(this, Transaction.getTransactionUid() + "Transaction already deleted", Toast.LENGTH_LONG).show();
                }
            }

//            if (myBool) {
////                Toast.makeText(this, "" + Account.getAccountName(), Toast.LENGTH_LONG).show();
//                double getBal = Account.getAccountBalance();
//                getBal += finalAmount;
//                Account.setAccountBalance(getBal);
//                AccountViewModel.updateAccount(this, Account);
//                myBool = false;
//            }

        };

        TransactionViewModel.getTransaction(this, UID).observe(this, getTransactionObserver);



    }

    public void toAccountActivity(View view) {
        finish();
    }


}