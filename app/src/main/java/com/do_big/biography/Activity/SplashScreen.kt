package com.do_big.biography.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.do_big.biography.R

class SplashScreen : AppCompatActivity() {
    private var pref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val title = findViewById<TextView>(R.id.appTitle)
        val moveAnimation = AnimationUtils.loadAnimation(this@SplashScreen, R.anim.move_up)
        title.startAnimation(moveAnimation)
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