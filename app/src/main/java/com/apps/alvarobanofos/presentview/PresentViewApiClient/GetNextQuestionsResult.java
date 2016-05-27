package com.apps.alvarobanofos.presentview.PresentViewApiClient;

import com.apps.alvarobanofos.presentview.Models.Question;

import java.util.ArrayList;

/**
 * Created by alvarobanofos on 23/5/16.
 */
public class GetNextQuestionsResult {

    private ArrayList<Question> questions;

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
