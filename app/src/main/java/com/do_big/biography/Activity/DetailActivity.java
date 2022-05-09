package com.do_big.biography.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.do_big.biography.Database.DatabaseHandler;
import com.do_big.biography.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    public static final String PREF_FILE_NAME = "PrefFile";
    private int fileNumber;
    private InputStream in;
    private LinearLayout layout;
    private DatabaseHandler db;
    private String storyId;
    private TextView mTextMessage;
    private TextToSpeech tts;

    private int ttsStatus;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_setting:
                    // mTextMessage.setText(R.string.title_setting);
                    Intent settingIntent = new Intent(DetailActivity.this, Settings.class);
                    startActivity(settingIntent);

                    return true;
                case R.id.navigation_listen:
                    //mTextMessage.setText(R.string.title_listen);
                    if(tts.isSpeaking()){
                        tts.stop();

                    }else
                    tts.speak(mTextMessage.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    return true;
                case R.id.navigation_share:

                    //mTextMessage.setText(R.string.title_share);
                    Intent shareintent = new Intent();
                    shareintent.setAction(Intent.ACTION_SEND);
                    shareintent.putExtra(Intent.EXTRA_TEXT,
                            mTextMessage.getText().toString() + "Short Stories");
                    shareintent.setType("text/plain");
                    startActivity(shareintent);

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        layout = findViewById(R.id.content);
        String key = getIntent().getStringExtra("file");
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                ttsStatus = status;
                tts.setLanguage(Locale.UK);

            }
        });

        mTextMessage = findViewById(R.id.message);
        mTextMessage.setMovementMethod(new ScrollingMovementMethod());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        storyId = key;
        AssetManager assetManager = getAssets();
        try {
            in = assetManager.open(storyId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTextMessage.setText(readTxt(in));
        fileNumber = Integer.parseInt(storyId.substring(0, storyId.indexOf(".")));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tts.stop();
    }

    @Override
    protected void onResume() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String textsize = settings.getString("TextSize", "18");
        mTextMessage.setTextSize(Float.parseFloat(textsize));
        boolean nightmode = settings.getBoolean("nightMode", false);
        if (nightmode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            mTextMessage.setTextColor(Color.BLACK);
            layout.setBackgroundResource(R.drawable.back2);
        }

        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tts.stop();
    }

    public void btnclick(View view) {
        int Id = view.getId();
        switch (Id) {
            case R.id.btn_next:

                Log.d("Btn", "next pressed" + fileNumber);
                if (fileNumber < 25) {
                    ++fileNumber;
                    String nextstory = fileNumber + ".txt";
                    Log.d("nextstory", nextstory);
                    AssetManager assetManager = getAssets();
                    try {
                        in = assetManager.open(nextstory);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mTextMessage.setText(readTxt(in));


                }
                break;
            case R.id.btn_previous:
                if (fileNumber > 1) {
                    --fileNumber;
                    String previousstory = fileNumber + ".txt";
                    Log.d("previous story", previousstory);
                    AssetManager assetManager = getAssets();
                    try {
                        in = assetManager.open(previousstory);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mTextMessage.setText(readTxt(in));


                }

                Log.d("Btn", "previous  pressed");
                break;
        }
    }

    private CharSequence readTxt(InputStream inputStream) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            if (inputStream != null) {
                i = inputStream.read();
                while (i != -1) {
                    byteArrayOutputStream.write(i);
                    i = inputStream.read();
                }
            }


            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return byteArrayOutputStream.toString();
    }
}
