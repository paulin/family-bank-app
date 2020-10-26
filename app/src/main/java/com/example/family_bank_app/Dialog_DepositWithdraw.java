package com.example.family_bank_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_DepositWithdraw extends AppCompatDialogFragment {

    //Status indicator
    public static final int STATUS_WITHDRAW = 1;
    public static final int STATUS_DEPOSIT = 2;

    private String action;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //builder.setTitle()

        return builder.create();
    }
}
