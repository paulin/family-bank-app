package com.example.family_bank_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    String AccountName[], AccountBalance[];
    Context context;






    public MyAdapter(Context ct, String[] s1, String[] s2 ) {
        context = ct;
        AccountName = s1;
        AccountBalance = s2;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.account_card_layout, parent, false );

        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        holder.text1.setText(AccountName[position]);

        holder.text2.setText(AccountBalance[position]);

    }


    @Override
    public int getItemCount() {
        if(AccountName == null){return 0;}
        return AccountName.length;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        ImageView image1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.AccountName);
            text2 = itemView.findViewById(R.id.AccountBalance);

        }
    }

}

