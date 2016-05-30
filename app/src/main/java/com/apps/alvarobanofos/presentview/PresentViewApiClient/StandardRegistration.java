package com.apps.alvarobanofos.presentview.PresentViewApiClient;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class StandardRegistration extends ApiResult {


    private boolean registered_yet, registrated, not_data_completed;

    public boolean isNot_data_completed() {
        return not_data_completed;
    }

    public void setNot_data_completed(boolean not_data_completed) {
        this.not_data_completed = not_data_completed;
    }

    public boolean isRegistrated() {
        return registrated;
    }

    public void setRegistrated(boolean registrated) {
        this.registrated = registrated;
    }

    public boolean isRegistered_yet() {
        return registered_yet;
    }

    public void setRegistered_yet(boolean registered_yet) {
        this.registered_yet = registered_yet;
    }
}
