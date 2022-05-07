package com.do_big.biography.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.do_big.biography.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    public static final int DELAY_MILLIS = 2500;
    SharedPreferences pref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView title = findViewById(R.id.appTitle);
        Animation moveanimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.move_up);
        title.startAnimation(moveanimation);
        pref = getSharedPreferences("com.do_big.biography", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, ListActivity.class));
                SplashScreen.this.finish();
            }
        }, DELAY_MILLIS);
    }


}
