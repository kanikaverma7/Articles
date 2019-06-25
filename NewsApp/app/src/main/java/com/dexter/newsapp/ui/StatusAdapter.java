package com.dexter.newsapp.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.dexter.newsapp.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StatusAdapter extends ArrayAdapter<ItemData> {
    private int groupid;
    private ArrayList<ItemData> list;
    private LayoutInflater inflater;
    private final Typeface font;

    public StatusAdapter(Activity context, int groupid, int id, ArrayList<ItemData> list) {
        super(context, id, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/UniversLTStd-LightCn.otf");

    }


    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            return false;
        } else {
            return true;
        }
    }

    @NotNull
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupid, parent, false);
        TextView textView = (TextView) itemView.findViewById(R.id.txt);
        textView.setText(list.get(position).getText());
        textView.setTypeface(font);
        return itemView;
    }

    public View getDropDownView(int position, View convertView, @NotNull ViewGroup
            parent) {

        View view = getView(position, convertView, parent);
        TextView tv = (TextView) view.findViewById(R.id.txt);
        if (position == 0) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return view;

    }
}