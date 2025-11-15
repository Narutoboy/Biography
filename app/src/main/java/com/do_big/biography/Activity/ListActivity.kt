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
import com.do_big.biography.ShortStoriesApplication
import com.do_big.biography.adapter.MyAdapter
import java.util.*

class ListActivity : AppCompatActivity() {
    private var listview: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        listview = findViewById(R.id.listView)
        //AppCompatDelegate.getDefaultNightMode();
        val adapter = MyAdapter(this@ListActivity, R.layout.list_item, R.id.title, ShortStoriesApplication.applicationAssetLoader.getNames().stories)
        listview?.adapter = adapter
        listview?.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            var i = i
            val detailIntent = Intent(this@ListActivity, DetailActivity::class.java)
            val storyNumber = ++i
            detailIntent.putExtra("file",
                ShortStoriesApplication.applicationAssetLoader.getNames().stories[i]
            )
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