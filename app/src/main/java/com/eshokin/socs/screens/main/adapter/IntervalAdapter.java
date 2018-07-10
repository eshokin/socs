package com.eshokin.socs.screens.main.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eshokin.socs.api.enumerations.Interval;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class IntervalAdapter extends ArrayAdapter<Pair<Interval, Integer>> {

    public IntervalAdapter(Context context, int resourceId, ArrayList<Pair<Interval, Integer>> list) {
        super(context, resourceId, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        Pair<Interval, Integer> item = getItem(position);
        if (item != null && item.second != null) {
            textView.setText(item.second);
        }
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        Pair<Interval, Integer> item = getItem(position);
        if (item != null && item.second != null) {
            textView.setText(item.second);
        }
        return textView;
    }
}
