package com.do_big.biography.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.do_big.biography.Activity.BioList;
import com.do_big.biography.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dell on 05-11-2017.
 */

public class MyAdapter extends BaseAdapter {
    Context ctx;
    List<StoryList> storylist;

    public MyAdapter(BioList bioList, int row, int textView, LinkedList<String> contactList) {
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf= (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View row=inf.inflate(R.layout.row,null);
        TextView tv=row.findViewById(R.id.textView);
        StoryList sl=storylist.get(i);
        tv.setText(sl.storyName);
        ImageView iv=row.findViewById(R.id.logo);
        iv.setImageResource(R.drawable.language_24dp);
        return null;
    }
}
