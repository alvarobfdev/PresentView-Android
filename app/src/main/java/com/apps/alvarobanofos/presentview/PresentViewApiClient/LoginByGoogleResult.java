package com.apps.alvarobanofos.presentview.PresentViewApiClient;

import com.apps.alvarobanofos.presentview.Models.User;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class LoginByGoogleResult extends ApiResult {


    private boolean registered;
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
}
