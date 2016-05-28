package com.apps.alvarobanofos.presentview.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.apps.alvarobanofos.presentview.R;

/**
 * Created by alvarobanofos on 28/5/16.
 */
public class WinnerDialog extends DialogFragment {

    public static WinnerDialog newInstance(String prize, String user, boolean me) {
        WinnerDialog f = new WinnerDialog();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("prize", prize);
        args.putString("user", user);
        args.putBoolean("me", me);
        f.setArguments(args);
        return f;
    }

    public WinnerDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String prize = getArguments().getString("prize");
        String user = getArguments().getString("user");
        boolean me = getArguments().getBoolean("me");
        String msg = String.format(getResources().getString(R.string.other_winner), prize, user);

        if(me)
            msg = String.format(getResources().getString(R.string.me_winner), prize);



        builder.setMessage(msg)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
