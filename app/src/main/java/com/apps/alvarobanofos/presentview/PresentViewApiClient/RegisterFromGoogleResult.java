package com.apps.alvarobanofos.presentview.PresentViewApiClient;

import com.apps.alvarobanofos.presentview.Models.User;

/**
 * Created by alvarobanofos on 4/3/16.
 */
public class RegisterFromGoogleResult extends ApiResult {

    private boolean registered_ok;
    private boolean alreadyRegistered;
    User user;

    public boolean isAlreadyRegistered() {
        return alreadyRegistered;
    }

    public void setAlreadyRegistered(boolean alreadyRegistered) {
        this.alreadyRegistered = alreadyRegistered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRegistered_ok() {
        return registered_ok;
    }

    public void setRegistered_ok(boolean registered_ok) {
        this.registered_ok = registered_ok;
    }
}
