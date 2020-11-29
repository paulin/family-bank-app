package com.example.family_bank_app;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.util.Date;
import java.util.List;

public class TransactionActivity extends AppCompatActivity implements Dialog_DeleteTransaction.DeleteTransactionDialogListener {
    private static final String TAG = TransactionActivity.class.getSimpleName();


    String note, date, status, transactionStatus;
    Double amount, currentBal;
    Long transactionUID, accountUID;
    boolean deleteTransactionOk, undoAccountBalanceOk;
    int position;

    TextView Note, Amount, CurrentBal, Date, Status;
    AccountViewModel viewModel;
    ImageButton deleteTrans;

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
        transactionUID = getIntent().getLongExtra("TRANSACTIONUID", 0);
        status = getIntent().getStringExtra("STATUS");
        position = getIntent().getIntExtra("POSITION", 0);
        accountUID = getIntent().getLongExtra("ACCOUNTUID", 0);

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

        deleteTrans = findViewById(R.id.deleteTransactionButton);
        deleteTrans.setOnClickListener(v -> deleteTransactionDialog());
    }


    public void deleteTransactionDialog() {
        Dialog_DeleteTransaction deleteDialog = new Dialog_DeleteTransaction();
        deleteDialog.show(getSupportFragmentManager(), "delete transaction dialog");
    }

    public void deleteTransaction(boolean deleteOk) {
//        Toast.makeText(this, "delete: " + UID, Toast.LENGTH_LONG).show();

        deleteTransactionOk = deleteOk;

        final Observer<TransactionEntity> getTransactionObserver = Transaction -> {
            if (Transaction == null){ return; }

            transactionStatus = Transaction.getTransactionStatus();

            if (deleteTransactionOk) {
                if (transactionStatus.equals("ok")) {
                    Toast.makeText(this, "Transaction deleted", Toast.LENGTH_LONG).show();
                    Transaction.setTransactionStatus("deleted");
                    double transactionAmount = Transaction.getTransactionAmount();
                    TransactionViewModel.createTransaction(this, Transaction);
                    deleteTransactionOk = false;

                    undoAccountBalance(transactionAmount);

                    Intent intent = new Intent(TransactionActivity.this, AccountActivity.class);
                    intent.putExtra("POSITION", position);
                    intent.putExtra("ACCOUNTUID", accountUID);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, Transaction.getTransactionUid() + "Transaction already deleted", Toast.LENGTH_LONG).show();
                }
            }

        };

        TransactionViewModel.getTransaction(this, transactionUID).observe(this, getTransactionObserver);

    }

    // Once transaction is delete, undo account balance
    public void undoAccountBalance(double transactionAmount) {

        undoAccountBalanceOk = true;
        final Observer<AccountEntity> getAccountObserver = Account -> {
            if (Account == null){ return; }

            if (undoAccountBalanceOk) {
                double getBal = Account.getAccountBalance();
                getBal += -1 * transactionAmount;
                Account.setAccountBalance(getBal);
                AccountViewModel.updateAccount(this, Account);
                undoAccountBalanceOk = false;
            }

        };
        AccountViewModel.getAccount(this, accountUID).observe(this, getAccountObserver);
    }

    // Fix back button to go to last activity.
    public void toAccountActivity(View view) {
        finish();
    }


}