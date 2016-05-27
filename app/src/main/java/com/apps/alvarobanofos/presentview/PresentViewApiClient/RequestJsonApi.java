package com.apps.alvarobanofos.presentview.PresentViewApiClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.apps.alvarobanofos.presentview.Helpers.NetworkErrors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class RequestJsonApi extends AsyncTask<Object, Void, Void> {

    private final static String TAG = "RequestJsonApi";
    String url;
    JSONObject jsonData;
    Context context;
    PresentViewApiClient.JsonApiRequestListener jsonApiRequestListener;

    public RequestJsonApi(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Object... params) {
        try {
            int apiType = (int) params[0];
            jsonData = (JSONObject) params[1];
            jsonApiRequestListener = (PresentViewApiClient.JsonApiRequestListener) params[2];


            switch (apiType) {
                case PresentViewApiClient.LOGIN_BY_GOOGLE:
                    loginByGoogle();
                    break;
                case PresentViewApiClient.REGISTER_FROM_GOOGLE:
                    registerFromGoogle();
                    break;
                case PresentViewApiClient.STANDARD_LOGIN:
                    standardLogin();
                    break;
                case PresentViewApiClient.VERIFY_TOKEN:
                    verifyToken();
                    break;
                case PresentViewApiClient.GET_NEXT_QUESTIONS:
                    getNextQuestions();
                    break;
                case PresentViewApiClient.GET_REVISION:
                    getRevision();
                    break;
                case PresentViewApiClient.SEND_ANSWER:
                    sendAnswer();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void loginByGoogle() throws JSONException {

        throwRequest("http://abf-ubuntu.cloudapp.net/PresentViewAdmin/public/api/login-by-google", LoginByGoogleResult.class);

    }

    private void registerFromGoogle() {
        throwRequest("http://abf-ubuntu.cloudapp.net/PresentViewAdmin/public/api/register-from-google", RegisterFromGoogleResult.class);
    }

    private void standardLogin() {
        throwRequest("http://abf-ubuntu.cloudapp.net/PresentViewAdmin/public/api/standard-login", StandardLoginResult.class);
    }

    private void verifyToken() {
        throwRequest("http://abf-ubuntu.cloudapp.net/PresentViewAdmin/public/api/verify-token", VerifyTokenResult.class);
    }

    private void getNextQuestions() {
        throwRequest("http://abf-ubuntu.cloudapp.net/PresentViewAdmin/public/api/get-next-questions", GetNextQuestionsResult.class);
    }

    private void getRevision() {
        throwRequest("http://abf-ubuntu.cloudapp.net/PresentViewAdmin/public/api/get-revision", RevisionResult.class);

    }

    private void sendAnswer() {
        throwRequest("http://abf-ubuntu.cloudapp.net/PresentViewAdmin/public/api/send-answer", AnswerSentResult.class);
    }

    private void throwRequest(String url, final Class returnClass) {

        Log.d(TAG, jsonData.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                        Log.d(TAG, response.toString());
                        Object objectResult = gson.fromJson(response.toString(), returnClass);
                        jsonApiRequestListener.jsonApiRequestResult(objectResult);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error.networkResponse == null) {
                            NetworkErrors.throwNetworkErrors(context, -1);
                        }
                        else NetworkErrors.throwNetworkErrors(context, error.networkResponse.statusCode);
                    }
                });

        SingletonRequestQueue.getInstance(context).add(jsObjRequest);
    }


}
