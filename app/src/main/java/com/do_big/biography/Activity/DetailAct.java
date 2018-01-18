package com.do_big.biography.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Locale;
import static android.R.attr.textSize;
public class DetailAct extends AppCompatActivity {
    public static final String PREF_FILE_NAME = "PrefFile";
    FrameLayout frameLayout;
    DatabaseHandler db;
    private TextView mTextMessage;
    private TextToSpeech tts;
    private int ttsStatus;
    int storyid;
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
                            "Share the data" + mTextMessage.getText().toString());
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
        frameLayout = (FrameLayout) findViewById(R.id.content);
        String key = getIntent().getStringExtra("id");
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                ttsStatus = status;
                tts.setLanguage(Locale.UK);

            }
        });

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextMessage.setMovementMethod(new ScrollingMovementMethod());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        storyid =Integer.parseInt(key);
        populateStory(storyid);
      }

    private String populateStory(int i) {
       // int i = Integer.parseInt(key);
        db = new DatabaseHandler(this);
        String data = String.valueOf(db.getContact(++i));
        mTextMessage.setText(data);
        db.close();
        return "story return karna h";
    }

    @Override
    protected void onResume() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String textsize = settings.getString("TextSize", "14");
        Log.d("textSize", "" + textSize);
        mTextMessage.setTextSize(Float.parseFloat(textsize));
        boolean nightmode = settings.getBoolean("nightMode", false);
        if (nightmode) {
            mTextMessage.setTextColor(Color.WHITE);
            frameLayout.setBackgroundColor(Color.BLACK);

           // mTextMessage.append("true");
        } else {
            mTextMessage.setTextColor(Color.BLACK);
            frameLayout.setBackgroundColor(Color.WHITE);
            //mTextMessage.append("false");
        }

        super.onResume();
    }

    //TODO next and previous should work
    public void btnclick(View view) {
        int Id = view.getId();
        switch (Id) {
            case R.id.btn_next:
                populateStory(++storyid);
                break;
            case R.id.btn_previous:
                populateStory(--storyid);
                break;
        }
    }
}
