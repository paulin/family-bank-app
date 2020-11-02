package com.example.family_bank_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_DepositWithdraw extends AppCompatDialogFragment {
    private static final String TAG = Dialog_DepositWithdraw.class.getSimpleName();

    //Status indicator
    public static final int STATUS_WITHDRAW = 1;
    public static final int STATUS_DEPOSIT = 2;

    private EditText editTextAmount, editTextMemo;
    private DepositWithdrawDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_deposit, null);

        //I want to have the title of the dialog change based on whether deposit or withdraw is clicked
        //But I'm currently having issues getting that to work

        builder.setView(view)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //positive action
                        String memo = editTextMemo.getText().toString();
                        Double value = Double.parseDouble(editTextAmount.getText().toString());
                        listener.sendText(value, memo);

                        // Demo to test transaction data
                        TransactionEntity transactionEntity = new TransactionEntity();

                        transactionEntity.setTransactionAmount(100.00);
                        transactionEntity.setTransactionDate("1/1/1111");
                        transactionEntity.setTransactionTitle("test transaction");
                        transactionEntity.setAccountMainUid(0);

                        TransactionViewModel.createTransaction(view.getContext(), transactionEntity);
                        Log.i(TAG, ""+TransactionViewModel.getTransaction(view.getContext(),0));
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //negative action
                        //do nothing
                    }
                });

        editTextAmount = view.findViewById(R.id.deposit_dialog);
        editTextMemo = view.findViewById(R.id.depwith_memo_dialog);

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (DepositWithdrawDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement DepositWithdrawDialogListener");
        }
    }

    public interface DepositWithdrawDialogListener{
        void sendText(double amount, String memo);
    }
}
