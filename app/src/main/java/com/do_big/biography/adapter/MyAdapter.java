package com.do_big.biography.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.do_big.biography.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dell on 05-11-2017.
 */

public class MyAdapter extends ArrayAdapter<String> {
    Context ctx;
    List<String> storylist;

    public MyAdapter(Context context, int row, int textView, LinkedList<String> list) {
        super(context, row, textView, list);
        ctx = context;
        storylist = list;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inf.inflate(R.layout.row, null);
        TextView tv = row.findViewById(R.id.rowText);
        tv.setText(storylist.get(i));//sl.storyName
        ImageView iv = row.findViewById(R.id.logo);
        iv.setImageResource(R.drawable.icon);
        return row;
    }
}
