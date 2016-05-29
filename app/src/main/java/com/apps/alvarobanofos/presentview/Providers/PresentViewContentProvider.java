package com.apps.alvarobanofos.presentview.Providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by alvarobanofos on 13/1/16.
 */
public class PresentViewContentProvider extends ContentProvider {

    private static final String DATABASE_NAME = "presentview.db";
    private static final int DATABASE_VERSION = 22;

    private static final String DATABASE_CREATE_USER = "CREATE TABLE user (_id integer, google_id text, email text, gender integer, provincia integer, ciudad integer, birthdate text, sim_id text, token text, user_id integer);";
    private static final String DATABASE_DROP_USER = "DROP TABLE IF EXISTS user;";

    private static final String DATABASE_CREATE_QUESTIONS = "CREATE TABLE questions (_id integer primary key autoincrement, title text, time_ini text, time_end text, duration integer, finished integer, prize integer, prize_title text, winner integer, winner_user_id integer, winner_name text, answered integer default 0);";
    private static final String DATABASE_DROP_QUESTIONS = "DROP TABLE IF EXISTS questions;";

    private static final String DATABASE_CREATE_ANSWERS = "CREATE TABLE possible_answers (_id integer primary key autoincrement, question_id integer, title text, img_uri text, selected integer, percentage numeric);";
    private static final String DATABASE_DROP_ANSWERS = "DROP TABLE IF EXISTS possible_answers;";

    private static final String DATABASE_CREATE_REVISIONS = "CREATE TABLE revisions (_id integer primary key autoincrement, revision integer);";
    private static final String DATABASE_DROP_REVISIONS = "DROP TABLE IF EXISTS revisions;";

    private static final String DATABASE_CREATE_RANKING = "CREATE TABLE ranking (_id integer primary key autoincrement, json text);";
    private static final String DATABASE_DROP_RANKING = "DROP TABLE IF EXISTS ranking;";

    private static final String INSERT_REVISION = "INSERT INTO revisions (revision)" +
            "VALUES (1);";

    private static final String INSERT_RANKING = "INSERT INTO ranking (json)" +
            "VALUES ('{}');";



    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_REVISION = "revisions";
    private static final String TABLE_ANSWERS = "possible_answers";
    private static final String TABLE_USER = "user";
    private static final String TABLE_RANKING = "ranking";


    private static final String _ID = "_id";


    MyDbHelper dbHelper;

    // Campos del content provider
    public static final String PROVIDER_NAME = "com.apps.alvarobanofos.presentview";
    public static final String URL_QUESTIONS = "content://" + PROVIDER_NAME + "/questions";
    public static final String URL_ANSWERS = "content://" + PROVIDER_NAME + "/answers";
    public static final String URL_REVISIONS = "content://" + PROVIDER_NAME + "/revision";
    public static final String URL_USER = "content://" + PROVIDER_NAME + "/user";
    public static final String URL_RANKING = "content://" + PROVIDER_NAME + "/ranking";


    public static final Uri CONTENT_URI_QUESTION = Uri.parse(URL_QUESTIONS);
    public static final Uri CONTENT_URI_ANSWERS = Uri.parse(URL_ANSWERS);
    public static final Uri CONTENT_URI_REVISIONS = Uri.parse(URL_REVISIONS);
    public static final Uri CONTENT_URI_USER = Uri.parse(URL_USER);
    public static final Uri CONTENT_URI_RANKING = Uri.parse(URL_RANKING);




    // Constantes usados para el content URI
    static final int QUESTIONS = 1;
    static final int ANSWERS = 2;
    static final int QUESTIONS_ID = 3;
    static final int ANSWERS_ID = 4;
    static final int REVISION = 5;
    static final int USER = 6;
    static final int RANKING = 7;



    // Mapeo de patrones de content URI a los valores definidos arriba
    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"questions",QUESTIONS);
        uriMatcher.addURI(PROVIDER_NAME,"answers",ANSWERS);
        uriMatcher.addURI(PROVIDER_NAME,"questions/#",QUESTIONS_ID);
        uriMatcher.addURI(PROVIDER_NAME,"answers/#",ANSWERS_ID);
        uriMatcher.addURI(PROVIDER_NAME,"revision",REVISION);
        uriMatcher.addURI(PROVIDER_NAME, "user", USER);
        uriMatcher.addURI(PROVIDER_NAME, "ranking", RANKING);


    }

    private SQLiteDatabase database = null;


    @Nullable
    @Override
    public String getType(Uri uri){
        switch (uriMatcher.match(uri)){
            //Todos los discos
            case QUESTIONS:
            case ANSWERS:
                return "vnd.android.cursor.dir/vnd."+PROVIDER_NAME;
            //Un disco particular
            case QUESTIONS_ID:
            case REVISION:
            case USER:
            case RANKING:
                return "vnd.android.cursor.item/vnd."+PROVIDER_NAME;

            case ANSWERS_ID:
                return "vnd.android.cursor.dir/vnd."+PROVIDER_NAME;
            default:
                throw new IllegalArgumentException("Unsupported URI "+uri);
        }
    }

    @Override
    public boolean onCreate() {


        dbHelper = new MyDbHelper(getContext());
        database = dbHelper.getWritableDatabase();

        return (database != null);

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String table = TABLE_ANSWERS;
        switch (uriMatcher.match(uri)){
            //Todos los registros de profes
            case ANSWERS:
                break;
            case ANSWERS_ID:
                queryBuilder.appendWhere("question_id" + " = " + uri.getLastPathSegment());
                break;
            case QUESTIONS:
                table = TABLE_QUESTIONS;
                break;
            case QUESTIONS_ID:
                table = TABLE_QUESTIONS;
                queryBuilder.appendWhere(_ID + " = " + uri.getLastPathSegment());
                break;
            case REVISION:
                table = TABLE_REVISION;
                break;
            case RANKING:
                table = TABLE_RANKING;
                break;
            case USER:
                table = TABLE_USER;
                queryBuilder.appendWhere(_ID + " = 1");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
        queryBuilder.setTables(table);

        return queryBuilder.query(database, null, selection,selectionArgs,null,null,sortOrder);
    }



    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String table = TABLE_QUESTIONS;
        switch (uriMatcher.match(uri)) {
            case QUESTIONS:
                break;
            case ANSWERS:
                table = TABLE_ANSWERS;
                break;
            case USER:
                values.put(_ID, "1");
                table = TABLE_USER;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
        long lastID = database.insert(table, null, values);

        if(lastID == -1)
            return null;

        return ContentUris.withAppendedId(CONTENT_URI_QUESTION, lastID);


                /*String table = TABLE;
        switch (uriMatcher.match(uri)){

            case ALUMNOS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
        long lastID = escuela_database.insert(table, null, values);

        if(lastID == -1)
            return null;

        return ContentUris.withAppendedId(CONTENT_URI_ALUMNOS, lastID);*/

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id = uri.getLastPathSegment();
        String table = TABLE_QUESTIONS;

        switch (uriMatcher.match(uri)) {
            case QUESTIONS_ID:
                break;
            case ANSWERS_ID:
                table = TABLE_ANSWERS;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
        String[] whereArgs = {id};
        return database.delete(table, "_id=?", whereArgs);
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        String id = uri.getLastPathSegment();
        String table = TABLE_QUESTIONS;
        String[] whereArgs = {id};

        switch (uriMatcher.match(uri)) {
            case QUESTIONS_ID:
                break;
            case ANSWERS_ID:
                table = TABLE_ANSWERS;
                break;
            case REVISION:
                table = TABLE_REVISION;
                whereArgs[0] = "1";
                break;
            case RANKING:
                table = TABLE_RANKING;
                whereArgs[0] = "1";
                break;
            case USER:
                table = TABLE_USER;
                whereArgs[0] = "1";
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }

        return database.update(table, values, "_id=?", whereArgs);
    }

    private static class MyDbHelper extends SQLiteOpenHelper {



        public MyDbHelper (Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_QUESTIONS);
            db.execSQL(DATABASE_CREATE_ANSWERS);
            db.execSQL(DATABASE_CREATE_REVISIONS);
            db.execSQL(DATABASE_CREATE_USER);
            db.execSQL(DATABASE_CREATE_RANKING);
            db.execSQL(INSERT_REVISION);
            db.execSQL(INSERT_RANKING);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DATABASE_DROP_QUESTIONS);
            db.execSQL(DATABASE_DROP_ANSWERS);
            db.execSQL(DATABASE_DROP_REVISIONS);
            db.execSQL(DATABASE_DROP_USER);
            db.execSQL(DATABASE_DROP_RANKING);
            onCreate(db);
        }
    }


}
