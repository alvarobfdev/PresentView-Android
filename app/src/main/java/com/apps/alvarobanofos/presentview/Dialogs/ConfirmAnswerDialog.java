package com.apps.alvarobanofos.presentview.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.apps.alvarobanofos.presentview.AnswersActivity;
import com.apps.alvarobanofos.presentview.Helpers.DateParser;
import com.apps.alvarobanofos.presentview.Helpers.DbHelper;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.AnswerSentResult;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.PresentViewApiClient;
import com.apps.alvarobanofos.presentview.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmAnswerDialog extends DialogFragment {

    int answerId;
    AnswersActivity activity;

    public static ConfirmAnswerDialog newInstance(String answer, int answerId) {
        ConfirmAnswerDialog f = new ConfirmAnswerDialog();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("answer", answer);
        args.putInt("answerId", answerId);
        f.setArguments(args);
        return f;
    }

    public ConfirmAnswerDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String answer = getArguments().getString("answer");
        answerId = getArguments().getInt("answerId");
        activity = (AnswersActivity) getActivity();

        builder.setMessage(String.format(getResources().getString(R.string.confirm_answer), answer))
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendAnswer();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void sendAnswer() {
        long now = DateParser.now();

        PresentViewApiClient.JsonApiRequestListener jsonApiRequestListener = new PresentViewApiClient.JsonApiRequestListener() {
            @Override
            public void jsonApiRequestResult(Object object) {
                AnswerSentResult answerSentResult = (AnswerSentResult) object;
                if(answerSentResult.isAnswerRegistered()) {
                    activity.confirmAnswer(answerSentResult.getUser_answer().getAnswer_id());
                }

            }
        };

        Map<String, String> json = new HashMap<>();
        json.put("time", ""+(now/1000));
        json.put("answerId", answerId+"");
        json.put("tokenUser", DbHelper.getInstance(getActivity()).getUser().getToken());


        PresentViewApiClient pvApi = new PresentViewApiClient(
                getActivity(),
                jsonApiRequestListener
        );

        pvApi.requestJsonApi(PresentViewApiClient.SEND_ANSWER, new JSONObject(json));
    }
}