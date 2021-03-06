package nscad.ad430_5216.family_bank_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class AccountActivity extends AppCompatActivity implements Dialog_DepositWithdraw.DepositWithdrawDialogListener, Dialog_DeleteAccount.DeleteAccountDialogListener {
    RecyclerView transactionRecyclerView;
    MyTransactionAdapter myTransactionAdapter;
    List<String> transactionName, date;
    List<Double> amount, currentBal;
    List<Long> UIDS;
    String name;
    Double balance;
    Long AccountUID;
    int position;
    boolean deleteAccountOk, deleteAllTransactionsOk;
    List<String> status;
    Boolean myBool;
    int transactionCount;

    AccountViewModel accountViewModel;
    TextView accountName, accountBal;
    //Call df.format(DOUBLE) to output a string with proper formatting
    DecimalFormat df = new DecimalFormat("0.00");
  
    private static final String TAG = "AccountActivity";

    // Inits for deposit and withdraw dialog
    Button btn_withdraw, btn_deposit;
    private int status_depositWithdraw;

    ImageButton deleteAcct;

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

        AccountUID = getIntent().getLongExtra("ACCOUNTUID", 0);
        position = getIntent().getIntExtra("POSITION", 0);

        accountName = findViewById(R.id.NameOfAccount);
        accountBal = findViewById(R.id.balance);

        transactionRecyclerView = findViewById(R.id.TransactionRecycler);

        myTransactionAdapter = new MyTransactionAdapter(this, transactionName , amount, currentBal, date, UIDS, status, AccountUID);
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

        AccountViewModel.getAccount(this, AccountUID).observe(this, getAccountObserver);

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
                currentBal.add(transaction.getTransactionCurrentBal());
                amount.add(transaction.getTransactionAmount());
                UIDS.add(transaction.getTransactionUid());
                date.add(transaction.getTransactionDate());
                // Strike through text for deleted
                status.add(transaction.getTransactionStatus());

                transactionCount++;


            }
            myTransactionAdapter.notifyDataSetChanged();
        };

        TransactionViewModel.getAllTransactions(this, AccountUID).observe(this, getTransactionsObserver);

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

        deleteAcct = findViewById(R.id.deleteAccountButton);
        deleteAcct.setOnClickListener(v -> deleteAccountDialog());
    }

    public void deleteAccountDialog() {
        Dialog_DeleteAccount deleteDialog = new Dialog_DeleteAccount();
        deleteDialog.show(getSupportFragmentManager(), "delete account dialog");
    }

    public void deleteAccount(boolean deleteOk) {
        deleteAccountOk = deleteOk;
        final Observer<AccountEntity> getAccountObserver = Account -> {
            if (Account == null){ return; }

            // Need to delete all transactions first, than account
            if (deleteAccountOk) {
                deleteTransactions(Account.getAccountUid(), true);
                AccountViewModel.deleteAccount(this, Account);
                Toast.makeText(this, "Account Deleted", Toast.LENGTH_LONG).show();
                deleteAccountOk = false;
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        AccountViewModel.getAccount(this, AccountUID).observe(this, getAccountObserver);
    }

    // Delete transactions associated with an account from the database
    public void deleteTransactions(long accountUID, boolean deleteOk) {

        deleteAllTransactionsOk = deleteOk;

        final Observer<List<TransactionEntity>> getTransactionObserver = TransactionsList -> {
            if (TransactionsList == null){ return; }

            if (deleteAllTransactionsOk) {
                for (int i=0; i < TransactionsList.size(); i++) {
                    TransactionEntity transaction = TransactionsList.get(i);
                    TransactionViewModel.deleteTransaction(this, transaction);
                }
                deleteAllTransactionsOk = false;
            }
        };
        TransactionViewModel.getAllTransactions(this, accountUID).observe(this, getTransactionObserver);
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
            transactionEntity.setTransactionCurrentBal(balance);
            transactionEntity.setAccountMainUid(AccountUID);

            TransactionViewModel.createTransaction(transactionRecyclerView.getContext(), transactionEntity);
        }


        //TODO: take this off main thread

        myBool = true;
        double finalAmount = formatAmount;
        final Observer<AccountEntity> getAccountObserver = Account -> {
            if (Account == null){ return; }

            if (myBool) {
                double getBal = Account.getAccountBalance();
                getBal += finalAmount;
                Account.setAccountBalance(getBal);
                AccountViewModel.updateAccount(this, Account);
                myBool = false;
            }

        };
        AccountViewModel.getAccount(this, AccountUID).observe(this, getAccountObserver);
    }

    public void toMainActivity(View view) {
        finish();
    }

   public void graphActivity(View view){

        // TODO: Fix for case where user has deleted transactions
        // Do not allow them to access graph is they have less than 1 transaction
        if (transactionCount < 2) {
            Toast.makeText(this, "You must have at least 2 transactions", Toast.LENGTH_LONG).show();
        } else {
            Double currentBal2[] =  new Double[currentBal.size()];
            currentBal2 = currentBal.toArray(currentBal2);
            double currentBal3[];
            currentBal3 = Stream.of(currentBal2).mapToDouble(Double::doubleValue).toArray();
            Log.d("this is my array", "currentbal2: " + Arrays.toString(currentBal3));

            String date2[] = new String[date.size()];
            date2 = date.toArray(date2);


            Intent intent = new Intent(AccountActivity.this, GraphActivity.class);
            intent.putExtra("BAL", currentBal3);
            intent.putExtra("DATE", date2);
            intent.putExtra("ACCT", name);
            startActivity(intent);
        }



    }
}