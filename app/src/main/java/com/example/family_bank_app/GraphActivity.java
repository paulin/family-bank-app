package com.example.family_bank_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.*;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.util.List;

public class GraphActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        double currentBal[] = {3.00, 4.00}; //getIntent().getDoubleArrayExtra("BAL");
        String date[] = {"3/20/2021", "3/21/2021"}; // getIntent().getStringArrayExtra("DATE");

        String hLabels[] = new String[date.length];
        String vLabels[] = new String[date.length];
        GraphView.GraphViewData[] graphViewData = new GraphView.GraphViewData[date.length];


        for(int i = 0; i < date.length; i++) {
            double z = currentBal[i];
            graphViewData[i] = (new DataPoint(Double.valueOf(i), z));
            hLabels[i] = date[i];
            vLabels[i] = String.valueOf(currentBal[i]);
        }

        GraphViewSeries mSeries = new GraphViewSeries(graphViewData);
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        graphView.addSeries(mSeries);
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            
        });
        graphView.setLa(hLabels);
        graphView.setVerticalLabels(vLabels);

        setContentView(R.layout.activity_graph);


    }
}
