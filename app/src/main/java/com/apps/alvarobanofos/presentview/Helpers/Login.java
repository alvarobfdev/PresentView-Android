package com.apps.alvarobanofos.presentview.Helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.apps.alvarobanofos.presentview.InitialActivity;
import com.apps.alvarobanofos.presentview.Models.User;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.PresentViewApiClient;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.VerifyTokenResult;
import com.apps.alvarobanofos.presentview.QuestionsActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alvarobanofos on 4/3/16.
 */
public class Login {
    private static Login login = null;

    Context context;

    private PresentViewApiClient.JsonApiRequestListener jsonApiRequestListener = new PresentViewApiClient.JsonApiRequestListener() {
        @Override
        public void jsonApiRequestResult(Object object) {
            VerifyTokenResult verifyTokenResult = (VerifyTokenResult) object;
            if (verifyTokenResult.isValidToken()) {
                User user = DbHelper.getInstance(context).getUser();
                loginUser(context, user);
            }
            else {
                Intent intent = new Intent(context, InitialActivity.class);
                context.startActivity(intent);
            }
        }

    };

    public static Login getInstance() {
        if(login == null)
            login = new Login();

        return login;
    }

    public void loginUser(Context context, User user) {
        DbHelper.getInstance(context).saveOrUpdateUser(user);
        Intent intent = new Intent(context, QuestionsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void  loginIfUserLogged(Context context) {

        this.context = context;

        User user = DbHelper.getInstance(context).getUser();
        if(user == null) {
            Intent intent = new Intent(context, InitialActivity.class);
            context.startActivity(intent);
            return;
        }

        PresentViewApiClient presentViewApiClient = new PresentViewApiClient(
                context,
                jsonApiRequestListener
        );

        Map< String, String > json = new HashMap<>();
        json.put("email", user.getEmail());
        json.put("token", user.getToken());

        presentViewApiClient.requestJsonApi(PresentViewApiClient.VERIFY_TOKEN, new JSONObject(json));
    }


}
