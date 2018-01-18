package com.do_big.biography.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.do_big.biography.Database.DatabaseHandler;
import com.do_big.biography.Database.Storys;
import com.do_big.biography.R;

import java.util.LinkedList;
public class BioList extends AppCompatActivity {
    ListView Biolist;
    Dialog searchdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Biolist = (ListView) findViewById(R.id.biolist);
        deleteDatabase("bioStory");
        DatabaseHandler db = new DatabaseHandler(this);
        db.addContact(new Storys("Akshay", "Sharma Programmer + android+ web design + web development + Never give Up!!!!"));
        db.addContact(new Storys("Elon  musk", "Enterprenur of the year"));
        db.addContact(new Storys("apj abdul", "President of india also known as the rocket men"));
        db.addContact(new Storys("petr ", "Russian Boy who win lot of the Compititive programming contest held by diffrent part in world like Topcoder , ioi and lot more,,,,,,,,,,,,,Russian Boy who win lot of the Compititive programming contest held by diffrent part in world like Topcoder , ioi and lot more"));
        populatelist();
        //TODO list auto populate (via database)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchdialog = new Dialog(BioList.this);
                searchdialog.setTitle("Filter By");
                searchdialog.setContentView(R.layout.search_dialog);
                searchdialog.show();
                Button dialogbtn = searchdialog.findViewById(R.id.btn1);
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        Biolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv1 = (TextView) view;
                Intent detailintent = new Intent(BioList.this, DetailAct.class);
                detailintent.putExtra("id", "" + i);
                startActivity(detailintent);
            }
        });
    }

    private void populatelist() {
        DatabaseHandler db = new DatabaseHandler(this);
        Cursor cursor = db.getAllContacts();
        Log.d("Main",cursor.toString());
        LinkedList<String> contactList = new LinkedList<>();
        if (cursor.moveToFirst()) {

            do {
                Storys story = new Storys();
                String title = story.setStoryTitle(cursor.getString(1));
             //   String strstory = story.setStory(cursor.getString(2));
                contactList.add(title);
            } while (cursor.moveToNext());
        }
//      MyAdapter myAdapter=new MyAdapter(BioList.this,R.layout.row,R.id.textView,contactList);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
       Log.d("aa",aa.toString());
        Biolist.setAdapter(aa);

    }

    public void dialogbtn(View view) {
        DatabaseHandler db = new DatabaseHandler(this);
        //TODO dialogs by names

        int id = view.getId();
        switch (id) {
            case R.id.btn1:
                Cursor cursor = db.getAllContacts();
                LinkedList<String> contactList = new LinkedList<>();
                if (cursor.moveToFirst()) {
                    do {
                        Storys story = new Storys();
                        String title = story.setStoryTitle(cursor.getString(1));
                        String strstory = story.setStory(cursor.getString(2));
                        contactList.add(title);
                    } while (cursor.moveToNext());
                }
                ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
                Biolist.setAdapter(aa);
                searchdialog.cancel();

        }
    }


}

