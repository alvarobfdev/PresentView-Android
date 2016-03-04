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

    private void throwRequest(String url, final Class returnClass) {

        Log.d(TAG, jsonData.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
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
