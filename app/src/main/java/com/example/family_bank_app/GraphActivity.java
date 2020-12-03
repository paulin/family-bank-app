package com.example.family_bank_app;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.*;


import java.util.Arrays;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;
    double[] currentBal;
    String date[], name;
    TextView Textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Textview = findViewById(R.id.NameOfGraph);

        currentBal = getIntent().getDoubleArrayExtra("BAL");
        date = getIntent().getStringArrayExtra("DATE");
        name = getIntent().getStringExtra("ACCT");
        Textview.setText(name);
        Log.d("this is my array", "arr: " + Arrays.toString(currentBal));
        Log.d("this is my array", "arr: " + Arrays.toString(date));
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries();


        for(int i = 0; i < currentBal.length; i++) {

            series.appendData(new DataPoint(i, currentBal[i]), true, currentBal.length);
        }

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX) {
                    if(value % 1 == 0){
                        int inum=(int)value;
                       return date[inum];
                    }

                    return "";
                }
                return  "$" + super.formatLabel(value, isValueX);
            }
        });
        graphView.setBackgroundColor(Color.WHITE);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);
        graphView.addSeries(series);




        /*
        for(int i = 0; i < date.length; i++) {
            double z = currentBal[i];
            graphViewData[i] = (new DataPoint(Double.valueOf(i), z));
            hLabels[i] = date[i];
            vLabels[i] = String.valueOf(currentBal[i]);
        }

        GraphViewSeries mSeries = new GraphViewSeries(graphViewData);



        */



     }


    }




