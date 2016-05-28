package com.apps.alvarobanofos.presentview.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alvarobanofos on 4/3/16.
 */
public class User implements Serializable {

    //Constantes columnas user SQLITE

    public static int ID_COLUMN = 0;
    public static int GOOGLE_ID_COLUMN = 1;
    public static int EMAIL_COLUMN = 2;
    public static int GENDER_COLUMN = 3;
    public static int PROVINCIA_COLUMN = 4;
    public static int CIUDAD_COLUMN = 5;
    public static int BIRTHDATE_COLUMN = 6;
    public static int SIM_ID_COLUMN = 7;
    public static int TOKEN_COLUMN = 8;
    public static int USER_ID = 9;



    String google_id, email, sim_id, token;
    int gender, provincia, ciudad, user_id;
    Date birthdate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getProvincia() {
        return provincia;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }

    public int getCiudad() {
        return ciudad;
    }

    public void setCiudad(int ciudad) {
        this.ciudad = ciudad;
    }

    public String getSim_id() {
        return sim_id;
    }

    public void setSim_id(String sim_id) {
        this.sim_id = sim_id;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
