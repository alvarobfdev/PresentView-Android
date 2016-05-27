package com.apps.alvarobanofos.presentview.Controllers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.apps.alvarobanofos.presentview.Helpers.DateParser;
import com.apps.alvarobanofos.presentview.Helpers.DbHelper;
import com.apps.alvarobanofos.presentview.Models.Answer;
import com.apps.alvarobanofos.presentview.Models.Question;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.GetNextQuestionsResult;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.PresentViewApiClient;
import com.apps.alvarobanofos.presentview.Providers.PresentViewContentProvider;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alvarobanofos on 23/5/16.
 */
public class QuestionsController {
    private static QuestionsController questionsController = null;
    private Context context;


    public static QuestionsController getInstance(Context context) {
        if(questionsController == null)
            questionsController = new QuestionsController();
        questionsController.context = context;
        return questionsController;
    }

    public void
    updateQuestionsTable() {

        PresentViewApiClient.JsonApiRequestListener jsonApiRequestListener =
                new PresentViewApiClient.JsonApiRequestListener() {
                    @Override
                    public void jsonApiRequestResult(Object object) {
                        GetNextQuestionsResult getNextQuestionsResult = (GetNextQuestionsResult) object;
                        updateQuestions(getNextQuestionsResult.getQuestions());
                        sendQuestionChangedEvent();

                    }
                };

        PresentViewApiClient pvApi = new PresentViewApiClient(context, jsonApiRequestListener);

        Map< String, String > json = new HashMap<>();
        json.put("void", "void");

        pvApi.requestJsonApi(PresentViewApiClient.GET_NEXT_QUESTIONS, new JSONObject(json));

    }

    public void sendQuestionChangedEvent() {
        Intent intent = new Intent("questionsChanged");
        intent.putExtra("message", "data");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void updateQuestions(ArrayList<Question> remoteQuestions) {
        ArrayList<Question> localQuestions = DbHelper.getInstance(context).getNextQuestions();

        for(Question remoteQuestion : remoteQuestions) {
            if(localQuestions.contains(remoteQuestion)) {
                Log.d("QC", "Actualizando...");
                updateQuestion(remoteQuestion);
            }
            else {
                Log.d("QC", "Insertando...");

                addQuestion(remoteQuestion);
            }
        }

        for(Question localQuestion : localQuestions) {
            if(!remoteQuestions.contains(localQuestion)) {
                Log.d("QC", "Borrando...");

                deleteQuestion(localQuestion);
            }
        }
    }

    public void updateQuestion(Question question) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("_id", question.getId());
        values.put("title", question.getTitle());
        values.put("time_ini", DateParser.getStringFromLong(question.getTime_ini().getTime(), DateParser.DEFAULT_SQL_DATETIME_PATTERN));
        values.put("duration", question.getDuration());
        values.put("duration", question.getDuration());
        if(question.isFinished())
            values.put("finished", 1);
        else values.put("finished", 0);
        resolver.update(Uri.parse(PresentViewContentProvider.URL_QUESTIONS + "/" + question.getId()), values, null, null);
        updateAnswers(question);
    }

    private void updateAnswers(Question question) {
        ContentResolver resolver = context.getContentResolver();
        for(Answer answer : question.getAnswers()) {
            ContentValues values = new ContentValues();
            values.put("_id", answer.getId());
            values.put("question_id", answer.getQuestion_id());
            values.put("title", answer.getTitle());
            values.put("img_uri", answer.getImg_uri());
            if(answer.isSelected())
                values.put("selected", 1);
            values.put("percentage", answer.getPercentage());
            resolver.update(Uri.parse(PresentViewContentProvider.URL_ANSWERS + "/" + answer.getId()), values, null, null);

        }
    }

    private void addQuestion(Question question) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("_id", question.getId());
        values.put("title", question.getTitle());
        values.put("time_ini", DateParser.getStringFromLong(question.getTime_ini().getTime(), DateParser.DEFAULT_SQL_DATETIME_PATTERN));
        values.put("duration", question.getDuration());
        if(question.isFinished())
            values.put("finished", 1);
        else values.put("finished", 0);
        resolver.insert(PresentViewContentProvider.CONTENT_URI_QUESTION, values);

        addAnswers(question);
    }

    private void addAnswers(Question question) {
        ContentResolver resolver = context.getContentResolver();
        for(Answer answer : question.getAnswers()) {
            ContentValues values = new ContentValues();
            values.put("_id", answer.getId());
            values.put("question_id", answer.getQuestion_id());
            values.put("title", answer.getTitle());
            values.put("img_uri", answer.getImg_uri());
            values.put("percentage", answer.getPercentage());
            resolver.insert(PresentViewContentProvider.CONTENT_URI_ANSWERS, values);

        }
    }



    private void deleteQuestion(Question question) {
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(Uri.parse(PresentViewContentProvider.URL_QUESTIONS + "/" + question.getId()), null, null);
    }

    public Question getQuestion(int id) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(PresentViewContentProvider.URL_QUESTIONS + "/" + id), null, null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            Question question = new Question(
                    cursor.getInt(Question.ID),
                    cursor.getString(Question.TITLE),
                    DateParser.getDateFromString(cursor.getString(Question.TIME_INI), DateParser.DEFAULT_SQL_DATETIME_PATTERN),
                    cursor.getInt(Question.DURATION),
                    cursor.getInt(Question.FINISHED)
            );
            cursor.close();

            question.setAnswers(getAnswersOfQuestion(id));

            return question;
        }
        return null;
    }

    public ArrayList<Answer> getAnswersOfQuestion(int questionId) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(PresentViewContentProvider.URL_ANSWERS + '/' + questionId), null, null, null, null);

        ArrayList<Answer> answers = new ArrayList<>();
        if(cursor != null) {
            while (cursor.moveToNext()) {
                answers.add(new Answer(
                        cursor.getInt(Answer.ID),
                        cursor.getInt(Answer.QUESTION_ID),
                        cursor.getString(Answer.TITLE),
                        cursor.getString(Answer.IMG_URI),
                        cursor.getInt(Answer.SELECTED),
                        cursor.getDouble(Answer.PERCENTAGE)
                ));
            }
            cursor.close();
        }
        return answers;
    }

    public ArrayList<String[]> getAnswersAdapter(Question question) {
        ArrayList<Answer> answers = question.getAnswers();

        ArrayList<String[]> arrayAdapter = new ArrayList<>();
        for(Answer answer : answers) {
            String[] row = {answer.getTitle(), answer.getImg_uri(), answer.getId()+"", answer.getPercentage()+"", answer.isSelected()+""};
            arrayAdapter.add(row);
        }

        return arrayAdapter;
    }




}
