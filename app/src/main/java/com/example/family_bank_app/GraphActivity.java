package com.example.family_bank_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;

import java.util.List;

public class GraphActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        double currentBal[]  = getIntent().getDoubleArrayExtra("BAL");
        List<String> date = getIntent().getStringArrayListExtra("DATE");
        GraphView graph = (GraphView) findViewById(R.id.graph);

    }
}
