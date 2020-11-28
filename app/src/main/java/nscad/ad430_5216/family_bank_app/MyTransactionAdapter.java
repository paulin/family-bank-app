package nscad.ad430_5216.family_bank_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_bank_app.R;

import java.util.List;


public class MyTransactionAdapter extends RecyclerView.Adapter<MyTransactionAdapter.ViewHolder> {
    private static final String TAG = MyTransactionAdapter.class.getSimpleName();

    List<String> transactionName, date, status;
    List<Double> amount, currentBal;
    List<Long> UIDS;
    Context context;
    CardView cardView;
    int position;

                                            //   name         amount            currentBal      date              UID,          status
    public MyTransactionAdapter(Context ct, List<String> s1, List<Double> s2, List<Double> s3, List<String> s4, List<Long> s5, List<String> s6) {
        context = ct;
        transactionName = s1;
        amount = s2;
        currentBal = s3;
        date = s4;
        UIDS = s5;
        status = s6;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.i(TAG, "in transadapt");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transaction_card_layout, parent, false );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.text1.setText(String.valueOf(date.get(position)));
        holder.text2.setText(String.valueOf(amount.get(position)));
        holder.text3.setText(transactionName.get(position));
        holder.text4.setText(String.valueOf(currentBal.get(position)));

        this.position = position + 1;

    }

    @Override
    public int getItemCount() {
        if(amount == null){return 0;}
        return amount.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text1, text2, text3, text4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.transactionDate);
            text2 = itemView.findViewById(R.id.transactionAmt);
            text3 = itemView.findViewById(R.id.transactionNote);
            text4 = itemView.findViewById(R.id.transactionCurrentBalance);
            cardView = itemView.findViewById(R.id.transactionCardView);

//            String status = text5.getText().toString();
//            Log.i(TAG, ""+position + "@@@@@@ " + status.get(position));
            if (status.get(position).equals("deleted")) {
                text1.setPaintFlags(text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text2.setPaintFlags(text2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text3.setPaintFlags(text3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text4.setPaintFlags(text4.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
                 goToTransactionActivity(position);
        }

        public void goToTransactionActivity(int position) {
            Long uid = UIDS.get(position);
            Intent intent = new Intent(context, TransactionActivity.class);
            intent.putExtra("TUID", uid);
            intent.putExtra("POSITION", position);
            context.startActivity(intent);

        }
    }
}




