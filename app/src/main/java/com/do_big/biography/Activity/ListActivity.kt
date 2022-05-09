package com.do_big.biography.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.do_big.biography.R
import com.do_big.biography.adapter.MyAdapter
import java.util.*

class ListActivity : AppCompatActivity() {
    private var listview: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        listview = findViewById(R.id.listView)
        //AppCompatDelegate.getDefaultNightMode();
        val items = LinkedList(
            Arrays.asList(
                "1 Shake off Your Problems",
                "2 The Elephant Rope",
                "3 Potatoes, Eggs, and Coffee Beans",
                "4 A Dish of Ice Cream",
                "5 Colonel Sanders | Kentucky Fried Chicken(KFC)",
                "6 The Obstacle in our Path",
                "7 Value",
                "8 A Very Special Bank Account",
                "9  Story of  the Butterfly",
                "10 Stories about goal setting",
                "11 The important things in life",
                "12 The Group of Frogs (Encouragement)",
                "13 A Pound of Butter (Honesty)",
                "14 The Obstacle In Our Path (Opportunity)",
                "15 The Butterfly (Struggles)",
                "16 Control Your Temper (Anger)",
                "17 The Blind Girl (Change)",
                "18 Puppies for Sale (Understanding)",
                "19 Box Full of Kisses (Love)",
                "20 One who read the future",
                "21 Choose Your Words Wisely",
                "22 Wealth without a Value",
                "23 The Reflection of Your Actions",
                "24 Think Before You Judge",
                "25 Smartest Man in the World"
            )
        )
        val adapter = MyAdapter(this@ListActivity, R.layout.row, R.id.rowText, items)
        listview?.adapter = adapter
        listview?.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            var i = i
            val detailIntent = Intent(this@ListActivity, DetailActivity::class.java)
            val storyNumber = ++i
            detailIntent.putExtra("file", "$storyNumber.txt")
            startActivity(detailIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val nightMode = settings.getBoolean("nightMode", false)
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.rate_us) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.do_big.biography")
                )
            )
            return true
        }
        if (id == R.id.share) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Short Story :) https://play.google.com/store/apps/details?id=com.do_big.biography"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}