package khoavin.sillylearningenglish.Pattern;


import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 24/06/2017.
 */

public class ConnectDialog extends DialogFragment {
    Listener onClickListener;

    public void setOnClickListener(Listener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fire_missiles)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onClickListener.onClick();
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
