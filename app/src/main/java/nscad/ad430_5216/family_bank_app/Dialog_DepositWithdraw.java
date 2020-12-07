package nscad.ad430_5216.family_bank_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.family_bank_app.R;

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
        editTextAmount = view.findViewById(R.id.deposit_dialog);
        editTextMemo = view.findViewById(R.id.depwith_memo_dialog);

        //Dynamically update title, hint text, and confirm button based on whether "Withdraw" or "Deposit" was clicked
        Bundle bundle = getArguments();
        int status = 0;
        String statusText = "", confirmButton = "Confirm";
        if (bundle != null){
            status = bundle.getInt("STATUS_TYPE");
        }
        switch(status){
            case STATUS_WITHDRAW:
                statusText = "Withdraw";
                confirmButton = "WITHDRAW";
                editTextAmount.setHint(R.string.withdraw_hint);
                break;
            case STATUS_DEPOSIT:
                statusText = "Deposit";
                confirmButton = "DEPOSIT";
                editTextAmount.setHint(R.string.deposit_hint);
                break;
            default:
                //Arguments failed
                //do nothing, use default values for dialog build (bad!)
                break;
        }

        builder.setTitle(statusText)
                .setView(view)
                .setPositiveButton(confirmButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //positive action
                        if(!editTextAmount.getText().toString().isEmpty()) {
                            //Check if given amount is empty before attempting to parse
                            String memo = editTextMemo.getText().toString();
                            Double value = Double.parseDouble(editTextAmount.getText().toString());
                            listener.sendText(value, memo, false);
                        } else {
                            Toast.makeText(getActivity(), "Please enter a value and note", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //negative action
                        //do nothing
                    }
                });

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
        void sendText(double amount, String memo, boolean deleteTransaction);
    }
}
