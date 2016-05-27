package com.apps.alvarobanofos.presentview.PresentViewApiClient;

import com.apps.alvarobanofos.presentview.Models.User;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class StandardLoginResult extends ApiResult {


    private boolean registered, is_google_account;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean is_google_account() {
        return is_google_account;
    }

    public void setIs_google_account(boolean is_google_account) {
        this.is_google_account = is_google_account;
    }
}
