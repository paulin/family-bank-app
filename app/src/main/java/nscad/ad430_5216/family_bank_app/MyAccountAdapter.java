package nscad.ad430_5216.family_bank_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class MyAccountAdapter extends RecyclerView.Adapter<MyAccountAdapter.ViewHolder> {

    List<String> AccountName, AccountBalance;
    List<Long> AccountUID;
    Context context;
    CardView cardView;

    public MyAccountAdapter(Context ct, List<String> s1, List<String> s2, List<Long> s3) {
        context = ct;
        AccountName = s1;
        AccountBalance = s2;
        AccountUID = s3;
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

        holder.text1.setText(AccountName.get(position));
        holder.text2.setText("$ " + AccountBalance.get(position));
    }

    @Override
    public int getItemCount() {
        if(AccountName == null){return 0;}
        return AccountName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text1, text2;

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
            Long UID = AccountUID.get(position);
            goToTransactionActivity(position, UID);
        }

        public void goToTransactionActivity(int position, Long UID) {
            Intent intent = new Intent(context, AccountActivity.class);

            intent.putExtra("POSITION", position);
            intent.putExtra("ACCOUNTUID", UID);
            context.startActivity(intent);
        }
    }
}



