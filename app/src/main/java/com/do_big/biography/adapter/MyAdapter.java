package com.do_big.biography.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.do_big.biography.R;
import com.do_big.biography.Story;

import java.util.List;

/**
 * Created by dell on 05-11-2017.
 */

public class MyAdapter extends ArrayAdapter<Story> {
    Context ctx;
    List<Story> storyList;

    public MyAdapter(Context context, int row, int textView, List<Story> list) {
        super(context, row, textView, list);
        ctx = context;
        storyList = list;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inf.inflate(R.layout.list_item, null);
        TextView tvTitle = row.findViewById(R.id.title);
        tvTitle.setText(storyList.get(i).getTitle());//sl.storyName
        TextView tvTypes = row.findViewById(R.id.additionalDetail);
        tvTypes.setText(""+storyList.get(i).getRating());
        ImageView iv = row.findViewById(R.id.rowIcon);
        TextView tvRating = row.findViewById(R.id.trailingDetailView);
        tvRating.setText("Rating"+storyList.get(i).getRating());
        iv.setImageResource(R.drawable.icon);
        return row;
    }
}
