package com.apps.alvarobanofos.presentview.Models;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import com.apps.alvarobanofos.presentview.Helpers.DateParser;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alvarobanofos on 22/5/16.
 */
public class Question {

    public static final int ID = 0;
    public static final int TITLE = 1;
    public static final int TIME_INI = 2;
    public static final int TIME_END = 3;
    public static final int DURATION = 4;
    public static final int FINISHED = 5;
    public static final int PRIZE = 6;
    public static final int PRIZE_TITLE = 7;
    public static final int WINNER = 8;
    public static final int WINNER_USER_ID = 9;
    public static final int WINNER_NAME = 10;


    String title;
    Date time_ini;
    int duration, id, winner_user_id;
    ArrayList<Answer> answers;
    boolean finished = false;
    boolean prize, winner;
    String prize_title, winner_name;

    public Question(int id, String title, Date time_ini, int duration, int finished, int prize, String prize_title, int winner) {
        this.title = title;
        this.time_ini = time_ini;
        this.duration = duration;
        this.id = id;
        this.finished = (finished > 0);
        this.prize = (prize > 0);
        this.prize_title = prize_title;
        this.winner = (winner > 0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime_ini() {
        return time_ini;
    }

    public void setTime_ini(Date time_ini) {
        this.time_ini = time_ini;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public boolean isPrize() {
        return prize;
    }

    public void setPrize(boolean prize) {
        this.prize = prize;
    }

    public String getPrize_title() {
        return prize_title;
    }

    public void setPrize_title(String prize_title) {
        this.prize_title = prize_title;
    }

    public int getWinner_user_id() {
        return winner_user_id;
    }

    public void setWinner_user_id(int winner_user_id) {
        this.winner_user_id = winner_user_id;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public String getWinner_name() {
        return winner_name;
    }

    public void setWinner_name(String winner_name) {
        this.winner_name = winner_name;
    }

    public String[] getSimpleDataSet() {
        Log.d("Question", getTime_ini().getTime()+"");
        Log.d("Question", DateParser.now()+"");

        String status = "";
        if(getTime_ini().getTime() > DateParser.now()) {
            status = "Próx";
        }
        else if((getTime_ini().getTime()+(getDuration()*1000)) > DateParser.now()) {
            status = "En curso";
        }
        else {
            status = "Finalizado";
        }
        Log.d("QUESTION", isPrize()+"");
        String[] dataSet = {getTitle(), status, ""+getId(), isPrize()+""};
        return dataSet;

    }

    @NonNull
    public static ArrayList<Question> getQuestionsFromCursor(Cursor cursor) {
        ArrayList<Question> questions = new ArrayList<Question>();
        if(cursor != null) {
            while(cursor.moveToNext()) {
                Question question = new Question(
                        cursor.getInt(Question.ID),
                        cursor.getString(Question.TITLE),
                        DateParser.getDateFromString(cursor.getString(Question.TIME_INI), DateParser.DEFAULT_SQL_DATETIME_PATTERN),
                        cursor.getInt(Question.DURATION),
                        cursor.getInt(Question.FINISHED),
                        cursor.getInt(Question.PRIZE),
                        cursor.getString(Question.PRIZE_TITLE),
                        cursor.getInt(Question.WINNER)
                );

                if(question.isWinner()) {
                    question.setWinner_user_id(cursor.getInt(Question.WINNER_USER_ID));
                    question.setWinner_name(cursor.getString(Question.WINNER_NAME));
                }

                questions.add(question);
            }
            cursor.close();
        }
        return questions;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Question) {
            Question compare = (Question) o;
            return this.getId() == compare.getId();
        }
        return false;
    }
}
