package com.apps.alvarobanofos.presentview.Helpers;

import android.content.Context;

import com.apps.alvarobanofos.presentview.Models.Provincia;
import com.apps.alvarobanofos.presentview.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by alvarobanofos on 25/2/16.
 */
public class ProvinciasFactory {


    public static Provincia[] load(Context context) throws FileNotFoundException, UnsupportedEncodingException {

        InputStream is = context.getResources().openRawResource(R.raw.codprov);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return load(br);

    }

    public static Provincia[] load (BufferedReader br) {
        Gson gson = new Gson();
        return gson.fromJson(br, Provincia[].class);
    }

    public static ArrayList<Provincia> toArrayList(Provincia[] provincias) {
        ArrayList<Provincia> provinciasArrayList = new ArrayList<>();
        for(Provincia provincia : provincias) {
            provinciasArrayList.add(provincia);
        }
        return provinciasArrayList;
    }


}
