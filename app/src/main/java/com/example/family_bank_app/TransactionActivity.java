package com.example.family_bank_app;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    String note[], amount[], currentBal[];
    TextView Note, Amount, CurrentBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);




        note = new String[]{"Cheese", "More Cheese", "PepperJack"};
        amount = new String[]{"5.00", "6.00", "7.00"};
        currentBal = new String[]{"20.00", "14.00", "7.00" };

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