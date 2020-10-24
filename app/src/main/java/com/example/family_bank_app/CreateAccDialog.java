package com.example.family_bank_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class CreateAccDialog  extends AppCompatDialogFragment {
    //Made following a video tutorial from Coding in Flow on youtube, shamelessly
    //https://www.youtube.com/watch?v=ARezg1D9Zd0

    private EditText editTextName;
    private EditText editTextValue;
    private CreateAccountDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_account, null);

        builder.setTitle("Create New Account")
                .setView(view)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() { // Extract Create string
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // This onClick needs to deal with empty string inputs.
                        String name = editTextName.getText().toString();
                        Double value = Double.parseDouble(editTextValue.getText().toString());
                        listener.sendText(name, value);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // Extract Cancel string
                        //do nothing
                    }
                });

        editTextName = view.findViewById(R.id.edit_account_name);
        editTextValue = view.findViewById(R.id.edit_account_balance);

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (CreateAccountDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement CreateAccountDialogListener");
        }
    }

    public interface CreateAccountDialogListener{
        void sendText(String name, Double balance);
    }
}
