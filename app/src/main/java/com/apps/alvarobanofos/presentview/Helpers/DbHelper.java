package com.apps.alvarobanofos.presentview.Helpers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.apps.alvarobanofos.presentview.Controllers.QuestionsController;
import com.apps.alvarobanofos.presentview.Controllers.RevisionController;
import com.apps.alvarobanofos.presentview.Models.Question;
import com.apps.alvarobanofos.presentview.Models.User;
import com.apps.alvarobanofos.presentview.Providers.PresentViewContentProvider;
import com.apps.alvarobanofos.presentview.RankingController;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alvarobanofos on 5/3/16.
 */
public class DbHelper {
    private static DbHelper dbHelper = null;
    private Context context;

    public static DbHelper getInstance(Context context) {
        if(dbHelper == null)
            dbHelper = new DbHelper();
        dbHelper.context = context;
        return dbHelper;
    }

    public void saveOrUpdateUser(User user) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(PresentViewContentProvider.CONTENT_URI_USER, null, null, null, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("google_id", user.getGoogle_id());
        contentValues.put("email", user.getEmail());
        contentValues.put("gender", user.getGender());
        contentValues.put("provincia", user.getProvincia());
        contentValues.put("ciudad", user.getCiudad());
        contentValues.put("birthdate", user.getBirthdate().toString());
        contentValues.put("sim_id", user.getSim_id());
        contentValues.put("token", user.getToken());

        //si hay un usuario...
        if (cursor != null && cursor.moveToFirst()) {
            resolver.update(PresentViewContentProvider.CONTENT_URI_USER, contentValues, null, null);
            cursor.close();
        }

        else {
            resolver.insert(PresentViewContentProvider.CONTENT_URI_USER, contentValues);
        }
    }

    public User getUser() {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(PresentViewContentProvider.CONTENT_URI_USER, null, null, null, null);

        //Si no existe usuario
        if(cursor == null || !cursor.moveToFirst()) {
            return null;
        }

        User user = new User();
        String format = "EEE MMM dd HH:mm:ss zzzz yyyy";
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format, Locale.ENGLISH);
        Date stringDate = simpledateformat.parse(cursor.getString(User.BIRTHDATE_COLUMN), pos);
        user.setBirthdate(stringDate);
        user.setCiudad(cursor.getInt(User.CIUDAD_COLUMN));
        user.setEmail(cursor.getString(User.EMAIL_COLUMN));
        user.setGender(cursor.getInt(User.GENDER_COLUMN));
        user.setGoogle_id(cursor.getString(User.GOOGLE_ID_COLUMN));
        user.setProvincia(cursor.getInt(User.PROVINCIA_COLUMN));
        user.setSim_id(cursor.getString(User.SIM_ID_COLUMN));
        user.setToken(cursor.getString(User.TOKEN_COLUMN));
        cursor.close();
        return user;

    }

    public ArrayList<Question> getNextQuestions() {
        ContentResolver resolver = context.getContentResolver();
        String time = DateParser.getStringFromLong(new Date().getTime(), DateParser.DEFAULT_SQL_DATETIME_PATTERN);
        String time_minus_duration = DateParser.getStringFromLong(new Date().getTime()-(120*1000), DateParser.DEFAULT_SQL_DATETIME_PATTERN);
        String selection = "time_ini > ? OR (time_ini < ? AND time_ini > ?) ";
        String selectionArgs[] = {time, time, time_minus_duration};
        String orderBy = "time_ini ASC";
        Cursor cursor = resolver.query(PresentViewContentProvider.CONTENT_URI_QUESTION, null, selection, selectionArgs, orderBy);
        ArrayList<Question> questions = Question.getQuestionsFromCursor(cursor);
        Log.d("DbHelper", time);
        Log.d("DbHelper", time_minus_duration);

        return questions;
    }

    public int getLocalRevision() {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(PresentViewContentProvider.CONTENT_URI_REVISIONS, null, null, null, null);

        //Si no existe revision
        if(cursor == null || !cursor.moveToFirst()) {
            return 0;
        }

        int revision = cursor.getInt(cursor.getColumnIndex("revision"));

        cursor.close();

        return revision;

    }

    public void updateDB(int revision) {
        (new RevisionController()).updateRevisionDB(context, revision);
        (new RankingController()).updateRankingDB(context);
        QuestionsController.getInstance(context).updateQuestionsTable();
    }

    public String getRankingJson() {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(PresentViewContentProvider.CONTENT_URI_RANKING, null, null, null, null);
        if(cursor == null || !cursor.moveToFirst()) {
            return "{}";
        }

        String ranking = cursor.getString(cursor.getColumnIndex("json"));
        cursor.close();
        return ranking;
    }




}
