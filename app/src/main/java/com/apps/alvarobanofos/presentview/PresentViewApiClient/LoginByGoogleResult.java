package com.apps.alvarobanofos.presentview.PresentViewApiClient;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class LoginByGoogleResult extends ApiResult {


    private boolean registered;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
