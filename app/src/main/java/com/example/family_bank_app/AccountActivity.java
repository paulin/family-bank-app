package com.example.family_bank_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.String.valueOf;

public class AccountActivity extends AppCompatActivity implements Dialog_DepositWithdraw.DepositWithdrawDialogListener {
    RecyclerView transactionRecyclerView;
    MyTransactionAdapter myTransactionAdapter;
    List<String> note, transactionName;
    List<Double> amount, currentBal;
    String name;
    Double balance;
    Date[] date;
    Date d;
    AccountViewModel viewModel;
    TextView accountName, accountBal;

    //inits for deposit and withdraw dialog
    EditText deposit_withdraw_dialog;
    Button btn_withdraw, btn_deposit;
    private int status_depositWithdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        note = new ArrayList<String>();
        amount = new ArrayList<Double>();
        currentBal = new ArrayList<Double>();
        name = "";
        balance = 0.0;

        viewModel = new AccountViewModel();

        //Account Loader from Database

        int pos = getIntent().getIntExtra("POSITION", 0);
        Long UID = getIntent().getLongExtra("UID", 0);
        AccountEntity account = new AccountEntity();
        AccountViewModel.getAccount(this, UID);

        name = account.getAccountName();
        balance = account.getAccountBalance();

        accountName = findViewById(R.id.NameOfAccount);
        accountBal = findViewById(R.id.balance);

        accountName.setText(name);
        accountBal.setText(valueOf(balance));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            d = sdf.parse("20201018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = new Date[]{d, d, d};

        transactionRecyclerView = findViewById(R.id.TransactionRecycler);
        /*
        Code for deposit and withdraw dialog below:
        */
        btn_deposit = findViewById(R.id.Btn_Deposit);
        btn_deposit.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                //CLICK DEPOSIT
                //Toast.makeText(getApplicationContext(), "click deposit", Toast.LENGTH_LONG).show();
                status_depositWithdraw = Dialog_DepositWithdraw.STATUS_DEPOSIT;
                depositWithdrawDialog();
            }
        });

        btn_withdraw = findViewById(R.id.Btn_Withdraw);
        btn_withdraw.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                //CLICK WITHDRAW
                //Toast.makeText(getApplicationContext(), "click withdraw", Toast.LENGTH_LONG).show();
                status_depositWithdraw = Dialog_DepositWithdraw.STATUS_WITHDRAW;
                depositWithdrawDialog();
            }
        });
    }

    //Called when either deposit or withdraw is clicked
    public void depositWithdrawDialog() {
        Dialog_DepositWithdraw depwithDialog = new Dialog_DepositWithdraw();
        depwithDialog.show(getSupportFragmentManager(), "deposit and withdraw dialog");
    }

    @Override
    public void sendText(double amount, String memo) {
        if (status_depositWithdraw == Dialog_DepositWithdraw.STATUS_WITHDRAW) {
            amount = amount * -1;
        }
        //Right now takes in a double dollar amount and string memo
        //Then toasts out an int cent value to change and the memo
        Toast.makeText(getApplicationContext(), "" + amount + " " + memo, Toast.LENGTH_LONG).show();
        //send transaction to Transaction handler
    }
}