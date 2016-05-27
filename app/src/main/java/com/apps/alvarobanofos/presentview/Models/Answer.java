package com.apps.alvarobanofos.presentview.Models;

/**
 * Created by alvarobanofos on 25/5/16.
 */
public class Answer {

    public static final int ID = 0;
    public static final int QUESTION_ID = 1;
    public static final int TITLE = 2;
    public static final int IMG_URI = 3;
    public static final int SELECTED = 4;
    public static final int PERCENTAGE = 5;



    int id, question_id;
    String title, img_uri;
    boolean selected = false;
    double percentage = 0.00;


    public Answer(int id, int question_id, String title, String img_uri, int selected, double percentage) {
        this.id = id;
        this.question_id = question_id;
        this.title = title;
        this.img_uri = img_uri;
        this.selected = (selected > 0);
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String img_uri) {
        this.img_uri = img_uri;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
