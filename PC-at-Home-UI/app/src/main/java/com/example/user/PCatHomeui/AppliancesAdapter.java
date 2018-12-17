package com.example.user.PCatHomeui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Inspiron on 2018/5/28.
 */

public class AppliancesAdapter extends ArrayAdapter<Appliances> {
    private int resourceId;
    public AppliancesAdapter(Context context, int textViewResourceId, List<Appliances> objects) {
        super(context, textViewResourceId, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Appliances appliances = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.appliancesName = (TextView) view.findViewById (R.id.appliances_name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.appliancesName.setText(appliances.getName());
        return view;
    }

    class ViewHolder {

        TextView appliancesName;

    }
}
