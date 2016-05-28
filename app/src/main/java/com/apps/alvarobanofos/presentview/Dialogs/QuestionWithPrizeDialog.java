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
public class QuestionWithPrizeDialog extends DialogFragment {

    public static QuestionWithPrizeDialog newInstance(String prize) {
        QuestionWithPrizeDialog f = new QuestionWithPrizeDialog();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("prize", prize);
        f.setArguments(args);
        return f;
    }

    public QuestionWithPrizeDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String prize = getArguments().getString("prize");

        builder.setMessage(String.format(getResources().getString(R.string.question_prize), prize))
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
