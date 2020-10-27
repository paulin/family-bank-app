package com.example.family_bank_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Dialog_CreateAcc.CreateAccountDialogListener {
    RecyclerView accountRecyclerView;
    MyAccountAdapter myAccountAdapter;
    String names[], balances[];
    ImageButton createAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        names = new String[]{"Jerry Law", "Mary Stringer"};
        balances = new String[]{"413.20$", "876.45$"};

        accountRecyclerView = findViewById(R.id.AccountRecycler);

        myAccountAdapter = new MyAccountAdapter(this, names, balances);
        accountRecyclerView.setAdapter(myAccountAdapter);
        accountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAccountAdapter.notifyDataSetChanged();

    }

    public void toAccountActivity(View view) {
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);

    }

   /* //Set up onclick listener for the create new account button
     createAcct =(ImageButton)findViewById(R.id.CreateAcct);
        createAcct.setOnClickListener(new View.OnClickListener(){
        public void onClick (View v){
        //Toast.makeText(getApplicationContext(), "click!", Toast.LENGTH_LONG).show();
        createAccDialog();
    }
    }); */


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
    public void sendText(String name, Double balance) {
        Toast.makeText(getApplicationContext(), name + " $" + balance, Toast.LENGTH_LONG).show();
    }
}