package com.example.family_bank_app;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    RecyclerView accountRecyclerView;
    MyAdapter myAdapter;
    String names[], balances[];
    ImageButton createAcct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        names = new String[] {"Jerry Law", "Mary Stringer" };
        balances = new String[]{"413.20$", "876.45$"};

        accountRecyclerView = findViewById(R.id.AccountRecycler);

        myAdapter = new MyAdapter(this,names ,balances );
        accountRecyclerView.setAdapter(myAdapter);
        accountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter.notifyDataSetChanged();

        createAcct = (ImageButton)findViewById(R.id.CreateAcct);
        createAcct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "click!", Toast.LENGTH_LONG).show();
            }
        });

    }
}