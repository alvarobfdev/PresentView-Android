package com.apps.alvarobanofos.presentview.PresentViewApiClient;

/**
 * Created by alvarobanofos on 22/2/16.
 */
public class ApiResult {

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
