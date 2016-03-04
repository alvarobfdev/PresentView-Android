package com.apps.alvarobanofos.presentview.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.Models.Municipio;
import com.apps.alvarobanofos.presentview.R;

import java.util.List;

/**
 * Created by alvarobanofos on 25/2/16.
 */
public class MunicipiosAdapter extends SpinnerAdapter {

    List<Municipio> items;

    public MunicipiosAdapter(Context context, int resource, List<Municipio> objects) {
        super(context, resource, objects, R.drawable.ic_map_white_24dp);
        items = objects;
        items.add(0, new Municipio(0, 0, context.getString(R.string.select_municipio)));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(R.id.tv_spinner_item);
        textView.setText(items.get(position).getNOMBRE());
        return view;
    }

    @Override
    public Municipio getItem(int position) {
        return items.get(position);
    }



    
}
