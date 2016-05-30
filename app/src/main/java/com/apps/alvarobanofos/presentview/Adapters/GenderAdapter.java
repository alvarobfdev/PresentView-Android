package com.apps.alvarobanofos.presentview.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.R;

import java.util.List;

/**
 * Created by alvarobanofos on 25/2/16.
 */
public class GenderAdapter extends SpinnerAdapter {

    List<String> items;

    public GenderAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects, R.drawable.ic_map_white_24dp);
        items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(R.id.tv_spinner_item);
        textView.setText(items.get(position));
        return view;
    }

    @Override
    public String getItem(int position) {
        return ""+position;
    }



    
}
