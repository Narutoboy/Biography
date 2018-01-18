package com.do_big.biography.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.do_big.biography.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences pref=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        pref=getSharedPreferences("com.do_big.biography",MODE_PRIVATE);
        TimerTask tt=new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, BioList.class));
                SplashScreen.this.finish();
            }
        };
        Timer t=new Timer();
        t.schedule(tt,1000);
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
