package com.do_big.biography.Activity

import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.os.Bundle
import com.do_big.biography.R
import android.widget.TextView
import android.view.animation.Animation
import android.content.Intent
import android.os.Handler
import android.view.animation.AnimationUtils

class SplashScreen : AppCompatActivity() {
    var pref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val title = findViewById<TextView>(R.id.appTitle)
        val moveanimation = AnimationUtils.loadAnimation(this@SplashScreen, R.anim.move_up)
        title.startAnimation(moveanimation)
        pref = getSharedPreferences("com.do_big.biography", MODE_PRIVATE)
        Handler().postDelayed({
            startActivity(Intent(this@SplashScreen, ListActivity::class.java))
            finish()
        }, DELAY_MILLIS.toLong())
    }

    companion object {
        const val DELAY_MILLIS = 2500
    }
}