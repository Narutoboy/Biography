package com.do_big.biography.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.speech.tts.TextToSpeech
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.do_big.biography.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*

class DetailActivity : AppCompatActivity() {
    private var fileNumber = 0
    private var `in`: InputStream? = null
    private var layout: LinearLayout? = null
    private var storyId: String? = null
    private var mTextMessage: TextView? = null
    private var tts: TextToSpeech? = null
    private var ttsStatus = 0
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_setting -> {
                    // mTextMessage.setText(R.string.title_setting);
                    val settingIntent = Intent(this@DetailActivity, Settings::class.java)
                    startActivity(settingIntent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_listen -> {
                    //mTextMessage.setText(R.string.title_listen);
                    if (tts!!.isSpeaking) {
                        tts!!.stop()
                    } else tts!!.speak(
                        mTextMessage!!.text.toString(),
                        TextToSpeech.QUEUE_FLUSH,
                        null
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_share -> {

                    //mTextMessage.setText(R.string.title_share);
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        mTextMessage!!.text.toString() + "Short Stories"
                    )
                    shareIntent.type = "text/plain"
                    startActivity(shareIntent)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        layout = findViewById(R.id.content)
        val key = intent.getStringExtra("file")
        tts = TextToSpeech(applicationContext) { status ->
            ttsStatus = status
            tts!!.language = Locale.UK
        }
        mTextMessage = findViewById(R.id.message)
        mTextMessage?.movementMethod = ScrollingMovementMethod()
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        storyId = key
        val assetManager = assets
        try {
            `in` = assetManager.open(storyId!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mTextMessage?.text = readTxt(`in`)
        fileNumber = storyId!!.substring(0, storyId!!.indexOf(".")).toInt()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        tts!!.stop()
    }

    override fun onResume() {
        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val textSize = settings.getString("TextSize", "18")
        mTextMessage!!.textSize = textSize!!.toFloat()
        val nightMode = settings.getBoolean("nightMode", false)
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            mTextMessage!!.setTextColor(Color.BLACK)
            layout!!.setBackgroundResource(R.drawable.back2)
        }
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        tts!!.stop()
    }

    fun buttonClick(view: View) {
        val _id = view.id
        when (_id) {
            R.id.btn_next -> {
                Log.d("Btn", "next pressed$fileNumber")
                if (fileNumber < 25) {
                    ++fileNumber
                    val nextStory = "$fileNumber.txt"
                    Log.d("nextstory", nextStory)
                    val assetManager = assets
                    try {
                        `in` = assetManager.open(nextStory)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    mTextMessage!!.text = readTxt(`in`)
                }
            }
            R.id.btn_previous -> {
                if (fileNumber > 1) {
                    --fileNumber
                    val previousStory = "$fileNumber.txt"
                    Log.d("previous story", previousStory)
                    val assetManager = assets
                    try {
                        `in` = assetManager.open(previousStory)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    mTextMessage!!.text = readTxt(`in`)
                }
                Log.d("Btn", "previous  pressed")
            }
        }
    }

    private fun readTxt(inputStream: InputStream?): CharSequence {
        val byteArrayOutputStream = ByteArrayOutputStream()
        var i: Int
        try {
            if (inputStream != null) {
                i = inputStream.read()
                while (i != -1) {
                    byteArrayOutputStream.write(i)
                    i = inputStream.read()
                }
            }
            inputStream!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return byteArrayOutputStream.toString()
    }

}