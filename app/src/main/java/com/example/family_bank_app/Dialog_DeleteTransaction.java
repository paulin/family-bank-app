package com.example.family_bank_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_DeleteTransaction extends AppCompatDialogFragment {

    private DeleteTransactionDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete, null);

        builder.setTitle("Warning!")
                .setMessage("Are you sure you wish to delete this transaction?\n\nTHIS CANNOT BE UNDONE!")
                .setView(view)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        Toast.makeText(getActivity(), "delete", Toast.LENGTH_LONG).show();
                          listener.deleteTransaction(true);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getActivity(), "don't delete", Toast.LENGTH_LONG).show();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (DeleteTransactionDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DeleteTransactionDialogListener");
        }
    }

    public interface DeleteTransactionDialogListener{
        void deleteTransaction(boolean deleteOK);
    }}
