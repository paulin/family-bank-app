package com.example.family_bank_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;


public class MyTransactionAdapter extends RecyclerView.Adapter<MyTransactionAdapter.ViewHolder> {

    String Note[], Amount[], CurBalance[];
    Date Date[];
    Context context;






    public MyTransactionAdapter(Context ct, String[] s1, String[] s2, String[] s3, Date[] s4 ) {
        context = ct;
        Note = s1;
        Amount = s2;
        CurBalance = s3;
        Date= s4;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transaction_card_layout, parent, false );

        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {



        holder.text2.setText(Amount[position]);

        holder.text3.setText(Note[position]);
        holder.text4.setText(CurBalance[position]);

    }


    @Override
    public int getItemCount() {
        if(Amount == null){return 0;}
        return Amount.length;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2, text3, text4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.transactionDate);
            text2 = itemView.findViewById(R.id.transactionAmt);
            text3 = itemView.findViewById(R.id.transactionNote);
            text4 = itemView.findViewById(R.id.transactionCurrentBalance);

        }
    }

}

