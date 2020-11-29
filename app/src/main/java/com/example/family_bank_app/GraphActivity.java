package com.example.family_bank_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.*;


import java.util.List;

public class GraphActivity extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;
    double currentBal[]  = {3.00, 4.00};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        GraphView graphView = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries();

        double y, x;
        x = -5.0;
        for(int i = 0; i < 500; i++) {
            x = x + .01;
            y = Math.sin(x);
            series.appendData(new DataPoint(x, y), true, 500);
        }
         //getIntent().getDoubleArrayExtra("BAL");
     /*   String date[] = {"3/20/2021", "3/21/2021"}; // getIntent().getStringArrayExtra("DATE");

        String hLabels[] = new String[date.length];
        String vLabels[] = new String[date.length];
        */

        graphView.addSeries(series);



        /*
        for(int i = 0; i < date.length; i++) {
            double z = currentBal[i];
            graphViewData[i] = (new DataPoint(Double.valueOf(i), z));
            hLabels[i] = date[i];
            vLabels[i] = String.valueOf(currentBal[i]);
        }

        GraphViewSeries mSeries = new GraphViewSeries(graphViewData);


        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){

        });
        */



     }


    }




