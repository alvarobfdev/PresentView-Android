package com.apps.alvarobanofos.presentview.PresentViewApiClient;

import com.apps.alvarobanofos.presentview.Models.UserAnswer;

/**
 * Created by alvarobanofos on 26/5/16.
 */
public class AnswerSentResult {
    boolean answerRegistered;
    String message;
    UserAnswer user_answer;

    public boolean isAnswerRegistered() {
        return answerRegistered;
    }

    public void setAnswerRegistered(boolean answerRegistered) {
        this.answerRegistered = answerRegistered;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserAnswer getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(UserAnswer user_answer) {
        this.user_answer = user_answer;
    }
}
