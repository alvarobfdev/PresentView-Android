package com.apps.alvarobanofos.presentview.PresentViewApiClient;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class PresentViewApiClient {

    public static final int LOGIN_BY_GOOGLE= 1;
    public static final int REGISTER_FROM_GOOGLE= 2;
    public static final int STANDARD_LOGIN = 3;
    public static final int VERIFY_TOKEN = 4;
    public static final int GET_NEXT_QUESTIONS = 5;
    public static final int GET_REVISION = 6;
    public static final int SEND_ANSWER = 7;
    public static final int GET_RANKING = 8;




    private JsonApiRequestListener jsonApiRequestListener;
    private Context context;
    private boolean transformResult = true;

    public PresentViewApiClient(Context context, JsonApiRequestListener jsonApiRequestListener) {
        this.context = context;
        this.jsonApiRequestListener = jsonApiRequestListener;

    }

    public void requestJsonApi(int APItype, JSONObject jsonData) {
        Object params[] = {APItype, jsonData, jsonApiRequestListener, transformResult};
        new RequestJsonApi(context).execute(params);
    }

    public interface JsonApiRequestListener {
        public void jsonApiRequestResult(Object object);
    }

    public boolean isTransformResult() {
        return transformResult;
    }

    public void setTransformResult(boolean transformResult) {
        this.transformResult = transformResult;
    }
}
