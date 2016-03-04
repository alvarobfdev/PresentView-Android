package com.apps.alvarobanofos.presentview.Helpers;

import android.content.Context;

import com.apps.alvarobanofos.presentview.Models.Municipio;
import com.apps.alvarobanofos.presentview.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by alvarobanofos on 25/2/16.
 */
public class MunicipiosFactory {


    public static Municipio[] load(Context context) throws FileNotFoundException, UnsupportedEncodingException {

        InputStream is = context.getResources().openRawResource(R.raw.codmun);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return load(br);

    }

    public static Municipio[] load (BufferedReader br) {
        Gson gson = new Gson();
        return gson.fromJson(br, Municipio[].class);
    }


}
