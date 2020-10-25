package com.example.family_bank_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



public class MyAccountAdapter extends RecyclerView.Adapter<MyAccountAdapter.ViewHolder> {

    String AccountName[], AccountBalance[];
    Context context;
    CardView cardView;

    public MyAccountAdapter(Context ct, String[] s1, String[] s2 ) {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text1, text2;
        ImageView image1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.AccountName);
            text2 = itemView.findViewById(R.id.AccountBalance);

            cardView = itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            goToTransactionActivity(position);
        }



        public void goToTransactionActivity(int position) {
            Intent intent = new Intent(context, AccountActivity.class);

            intent.putExtra("POSITION", position);
            context.startActivity(intent);


        }
        }
    }



