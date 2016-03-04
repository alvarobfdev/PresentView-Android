package com.apps.alvarobanofos.presentview.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.Models.Provincia;
import com.apps.alvarobanofos.presentview.R;

import java.util.List;

/**
 * Created by alvarobanofos on 25/2/16.
 */
public class ProvinciasAdapter extends SpinnerAdapter {

    List<Provincia> items;

    public ProvinciasAdapter(Context context, int resource, List<Provincia> objects) {
        super(context, resource, objects, R.drawable.ic_map_white_24dp);
        items = objects;
        items.add(0, new Provincia(0, context.getString(R.string.select_provincia)));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(R.id.tv_spinner_item);
        textView.setText(items.get(position).getName());
        return view;
    }

    @Override
    public Provincia getItem(int position) {
        return items.get(position);
    }
}
