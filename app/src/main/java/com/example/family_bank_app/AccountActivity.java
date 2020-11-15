package com.example.family_bank_app;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static java.lang.String.format;
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
    int position;
    List<String> status;

    Boolean myBool;
    Boolean deleteTransaction;
    double lastTransactionAmount;


    AccountViewModel accountViewModel;
//    TransactionViewModel transactionViewModel;
    TextView accountName, accountBal;
    //Call df.format(DOUBLE) to output a string with proper formatting
    DecimalFormat df = new DecimalFormat("0.00");
  
    private static final String TAG = "AccountActivity";

    //inits for deposit and withdraw dialog
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
        date = new ArrayList<String>();
        name = "";
        balance = 0.0;
        status = new ArrayList<String>();


        accountViewModel = new AccountViewModel();

        UID = getIntent().getLongExtra("UID", 0);
        position = getIntent().getIntExtra("POSITION", 0);

        accountName = findViewById(R.id.NameOfAccount);
        accountBal = findViewById(R.id.balance);

        transactionRecyclerView = findViewById(R.id.TransactionRecycler);

        myTransactionAdapter = new MyTransactionAdapter(this, transactionName , amount, currentBal, date, UIDS, status);
        transactionRecyclerView.setAdapter(myTransactionAdapter);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myTransactionAdapter.notifyDataSetChanged();

        final Observer<AccountEntity> getAccountObserver = Account -> {
            if (Account == null) {
                return;
            }
            name = Account.getAccountName();
            balance = Account.getAccountBalance();

            StringBuilder builderBalance = new StringBuilder();
            builderBalance.append("Balance: $");
            builderBalance.append(df.format(balance));

            accountName.setText(name);
            accountBal.setText(builderBalance);
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
            status.clear();

            //TODO: transaction display value will likely need to be formatted too

            // This re-updates for each new transaction change. Can we optimize so it only runs initially?
            for(int i=0; i < transactionEntities.size();i++) {
                Log.i(TAG, "updated " + i);
                TransactionEntity transaction = transactionEntities.get(i);
                transactionName.add(transaction.getTransactionTitle());
                currentBal.add(transaction.getTransactionAmount());
                amount.add(transaction.getTransactionAmount());
                UIDS.add(transaction.getTransactionUid());
                date.add(transaction.getTransactionDate());
                // Strike through text for deleted
                status.add(transaction.getTransactionStatus());


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
    public void sendText(double amount, String memo, boolean deleteTransaction) {
        //truncate value to two decimal places
        DecimalFormat truncate = new DecimalFormat("#.##");
        truncate.setRoundingMode(RoundingMode.DOWN); //Throw away any entered decimal places past two
        double formatAmount = Double.parseDouble(truncate.format(amount));

        if (status_depositWithdraw == Dialog_DepositWithdraw.STATUS_WITHDRAW) {
            formatAmount = formatAmount * -1;
        }

        // Create new transaction in database if not delete action
        if (!deleteTransaction) {
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setTransactionAmount(formatAmount);
            transactionEntity.setTransactionDate(new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()));
            transactionEntity.setTransactionTitle(memo);
            transactionEntity.setAccountMainUid(UID);

            TransactionViewModel.createTransaction(transactionRecyclerView.getContext(), transactionEntity);
        }


//        Log.i(TAG, ""+TransactionViewModel.getTransaction(transactionRecyclerView.getContext(), 0) );


//        AccountEntity accEnt = AccountViewModel.getAccountEntity(this, UID);
//        double workingBal = accEnt.getAccountBalance();
//        workingBal += amount;
//        accEnt.setAccountBalance(workingBal);
//        //TODO: take this off main thread

        myBool = true;
        double finalAmount = formatAmount;
        final Observer<AccountEntity> getAccountObserver = Account -> {
            if (Account == null){ return; }

            if (myBool) {
//                Toast.makeText(this, "" + Account.getAccountName(), Toast.LENGTH_LONG).show();
                double getBal = Account.getAccountBalance();
                getBal += finalAmount;
                Account.setAccountBalance(getBal);
                AccountViewModel.updateAccount(this, Account);
                myBool = false;
            }

        };
        AccountViewModel.getAccount(this, UID).observe(this, getAccountObserver);
    }

    public void deleteLastTransaction (View view) {
//        Toast.makeText(this, "delete " + lastTransactionUid, Toast.LENGTH_LONG).show();

        // set status to deleted
        deleteTransaction = true;
        final Observer<TransactionEntity> getLastTransactionObserver = Transaction -> {
            if (Transaction == null){
                Toast.makeText(this, "There are no more transactions", Toast.LENGTH_LONG).show();
            } else if (deleteTransaction) {
                lastTransactionAmount = Transaction.getTransactionAmount();
//                Toast.makeText(this, "" + lastTransactionAmount, Toast.LENGTH_LONG).show();
                Transaction.setTransactionStatus("deleted");
                TransactionViewModel.createTransaction(this, Transaction);

                // Undo last OK status transaction in account balance
                sendText( -1 * lastTransactionAmount, "", true);
                deleteTransaction = false;

                // reload activity to reflect strikethrough item
                Intent intent = new Intent(AccountActivity.this, AccountActivity.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("UID", UID);
                finish();
                startActivity(intent);

            }

        };

        TransactionViewModel.getLastOkTransaction(this, "ok").observe(this, getLastTransactionObserver);

        // Ensure transaction has strike-though or something
        // popconfirm for delete

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