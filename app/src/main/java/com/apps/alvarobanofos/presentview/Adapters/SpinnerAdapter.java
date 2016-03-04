package com.apps.alvarobanofos.presentview.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.apps.alvarobanofos.presentview.R;

import java.util.List;

/**
 * Created by alvarobanofos on 25/2/16.
 */
public class SpinnerAdapter extends ArrayAdapter {

    List<?> items;
    Context context;
    int iconHint;


    public SpinnerAdapter(Context context, int resource, List<?> objects, int iconHint) {
        super(context, resource, objects);
        items = objects;
        this.context = context;
        this.iconHint = iconHint;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.custom_spinner, null);


        if(position == 0) {
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_spinner_item);
            imageView.setImageResource(iconHint);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(position == 0) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            View view = mInflater.inflate(R.layout.custom_spinner, null);
            return view;
        }
        return getView(position, convertView, parent);
    }


}
