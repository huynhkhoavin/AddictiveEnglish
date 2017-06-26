package khoavin.sillylearningenglish.Pattern;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 26/06/2017.
 */

public class YesNoDialog extends DialogFragment {
    ConnectDialog.Listener onPositiveListener;
    ConnectDialog.Listener onNegativeListenter;

    String Message = "";

    public void setMessage(String message) {
        Message = message;
    }

    public void setOnNegativeListenter(ConnectDialog.Listener onNegativeListenter) {
        this.onNegativeListenter = onNegativeListenter;
    }

    public void setOnPositiveListener(ConnectDialog.Listener onPositiveListener) {
        this.onPositiveListener = onPositiveListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(Message)
                .setPositiveButton(R.string.alert_positive_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onPositiveListener.onClick();
                        YesNoDialog.this.dismiss();
                    }
                })
                .setNegativeButton(R.string.alert_negative_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        YesNoDialog.this.dismiss();
                    }
                })
        ;

        // Create the AlertDialog object and return it
        return builder.create();
    }
    public interface Listener{
        void onClick();
    }
}
