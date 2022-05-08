package com.do_big.biography.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.do_big.biography.R;
import com.do_big.biography.adapter.MyAdapter;

import java.util.Arrays;
import java.util.LinkedList;

public class ListActivity extends AppCompatActivity {
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listview = findViewById(R.id.listView);
        LinkedList<String> items = new LinkedList<String>(Arrays.asList(

                "1 Shake off Your Problems",
                "2 The Elephant Rope",
                "3 Potatoes, Eggs, and Coffee Beans",
                "4 A Dish of Ice Cream",
                "5 Colonel Sanders | Kentucky Fried Chicken(KFC)",
                "6 The Obstacle in our Path",
                "7 Value",
                "8 A Very Special Bank Account",
                "9  Story of  the Butterfly",
                "10 Stories about goal setting",
                "11 The important things in life",
                "12 The Group of Frogs (Encouragement)",
                "13 A Pound of Butter (Honesty)",
                "14 The Obstacle In Our Path (Opportunity)",
                "15 The Butterfly (Struggles)",
                "16 Control Your Temper (Anger)",
                "17 The Blind Girl (Change)",
                "18 Puppies for Sale (Understanding)",
                "19 Box Full of Kisses (Love)",
                "20 One who read the future",
                "21 Choose Your Words Wisely",
                "22 Wealth without a Value",
                "23 The Reflection of Your Actions",
                "24 Think Before You Judge",
                "25 Smartest Man in the World"));
        MyAdapter adapter = new MyAdapter(ListActivity.this, R.layout.row, R.id.rowText, items);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailIntent = new Intent(ListActivity.this, DetailActivity.class);
                int storyNumber = ++i;
                detailIntent.putExtra("file", "" + storyNumber + ".txt");
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.rate_us) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.do_big.biography")));
            return true;
        }
        if (id == R.id.share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Short Story :) https://play.google.com/store/apps/details?id=com.do_big.biography");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
