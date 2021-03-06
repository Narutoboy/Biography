package com.do_big.biography.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.do_big.biography.Database.DatabaseHandler;
import com.do_big.biography.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static android.R.attr.textSize;

public class DetailAct extends AppCompatActivity {
    public static final String PREF_FILE_NAME = "PrefFile";
    int fileno;
    InputStream in;
    FrameLayout frameLayout;
    DatabaseHandler db;
    String storyid;
    private TextView mTextMessage;
    private TextToSpeech tts;
    private InterstitialAd mInterstitialAd;

    private int ttsStatus;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_setting:
                    // mTextMessage.setText(R.string.title_setting);
                    Intent settingIntent = new Intent(DetailAct.this, Settings.class);
                    startActivity(settingIntent);

                    return true;
                case R.id.navigation_listen:
                    //mTextMessage.setText(R.string.title_listen);
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
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-9084411889674439~8429350092");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9084411889674439/5038444588");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        frameLayout = findViewById(R.id.content);
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
        storyid = key;
        AssetManager assetManager = getAssets();
        try {
            in = assetManager.open(storyid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTextMessage.setText(readTxt(in));
        fileno = Integer.parseInt(storyid.substring(0, storyid.indexOf(".")));

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
        Log.d("textSize", "" + textSize);
        mTextMessage.setTextSize(Float.parseFloat(textsize));
        boolean nightmode = settings.getBoolean("nightMode", false);
        if (nightmode) {
            mTextMessage.setTextColor(Color.WHITE);
            frameLayout.setBackgroundResource(R.drawable.nightback);

            // mTextMessage.append("true");
        } else {
            mTextMessage.setTextColor(Color.BLACK);
            frameLayout.setBackgroundResource(R.drawable.back);
            //mTextMessage.append("false");
        }

        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tts.stop();
    }

    public void btnclick(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        int Id = view.getId();
        switch (Id) {
            case R.id.btn_next:

                Log.d("Btn", "next pressed" + fileno);
                if (fileno < 25) {
                    ++fileno;
                    String nextstory = fileno + ".txt";
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
                if (fileno > 1) {
                    --fileno;
                    String previousstory = fileno + ".txt";
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
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return byteArrayOutputStream.toString();
    }
}
