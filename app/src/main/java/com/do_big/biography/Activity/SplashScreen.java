package com.do_big.biography.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.do_big.biography.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences pref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView title = findViewById(R.id.appTitle);
        Animation moveanimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.move_up);
        title.startAnimation(moveanimation);
        pref = getSharedPreferences("com.do_big.biography", MODE_PRIVATE);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, ListActivity.class));
                SplashScreen.this.finish();
            }
        };
        Timer t = new Timer();
        t.schedule(tt, 2500);
    }

//install database in firstrun
    /*@Override
    protected void onResume() {
        super.onResume();
        if(pref.getBoolean("firstlunch",true)){
            //
            pref.edit().putBoolean("firstlunch",false).commit();
        }
    }*/
}
