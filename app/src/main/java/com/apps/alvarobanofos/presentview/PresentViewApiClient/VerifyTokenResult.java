package com.apps.alvarobanofos.presentview.PresentViewApiClient;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class VerifyTokenResult extends ApiResult {


    private boolean isValidToken;

    public boolean isValidToken() {
        return isValidToken;
    }

    public void setValidToken(boolean validToken) {
        isValidToken = validToken;
    }
}
