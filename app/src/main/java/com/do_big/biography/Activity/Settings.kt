package com.do_big.biography.Activity

import android.content.Context
import com.do_big.biography.Activity.AppCompatPreferenceActivity
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import android.os.Bundle
import com.do_big.biography.Activity.Settings.MainPreferenceFragment
import android.preference.PreferenceFragment
import com.do_big.biography.R
import android.preference.Preference
import android.preference.Preference.OnPreferenceClickListener
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.ListPreference
import android.preference.RingtonePreference
import android.text.TextUtils
import android.media.Ringtone
import android.media.RingtoneManager
import android.preference.EditTextPreference
import android.preference.CheckBoxPreference
import android.os.Build
import android.content.pm.PackageManager
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.MenuItem

class Settings : AppCompatPreferenceActivity() {
    override fun onResume() {
        super.onResume()
        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val nightmode = settings.getBoolean("nightMode", false)
        if (nightmode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        fragmentManager.beginTransaction().replace(android.R.id.content, MainPreferenceFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class MainPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_main)
            // feedback preference click listener
            val myPref = findPreference(getString(R.string.key_send_feedback))
            myPref.onPreferenceClickListener = OnPreferenceClickListener {
                sendFeedback(activity)
                true
            }
        }
    }

    companion object {
        private val TAG = Settings::class.java.simpleName
        private val settings: String? = null
        private val sBindPreferenceSummaryToValueListener =
            OnPreferenceChangeListener { preference, newValue ->
                val stringValue = newValue.toString()
                if (preference is ListPreference) {
                    val listPreference = preference
                    val index = listPreference.findIndexOfValue(stringValue)
                    preference.setSummary(
                        if (index >= 0) listPreference.entries[index] else null
                    )
                } else if (preference is RingtonePreference) {
                    if (TextUtils.isEmpty(stringValue)) {
                        // Empty values correspond to 'silent' (no ringtone).
                        preference.setSummary(R.string.pref_ringtone_silent)
                    } else {
                        val ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue)
                        )
                        if (ringtone == null) {
                            // Clear the summary if there was a lookup error.
                            preference.setSummary(R.string.summary_choose_ringtone)
                        } else {
                            // Set the summary to reflect the new ringtone display
                            // name.
                            val name = ringtone.getTitle(preference.getContext())
                            preference.setSummary(name)
                        }
                    }
                } else if (preference is EditTextPreference) {
                    if (preference.getKey() == "key_gallery_name") {
                        // update the changed gallery name to summary filed
                        preference.setSummary(stringValue)
                    }
                } else if (preference is CheckBoxPreference) {
                    if (preference.getKey() == "key_night_mode") {
                        // update the changed gallery name to summary filed
                        preference.setSummary(stringValue)
                        Log.d("stringValue", stringValue)
                    }
                } else {
                    preference.summary = stringValue
                }
                true
            }

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener =
                sBindPreferenceSummaryToValueListener
            sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager
                    .getDefaultSharedPreferences(preference.context)
                    .getString(preference.key, "")
            )
        }

        fun sendFeedback(context: Context) {
            var body: String? = null
            try {
                body = context.packageManager.getPackageInfo(context.packageName, 0).versionName
                body = """

-----------------------------
Please don't remove this information
 Device OS: Android 
 Device OS version: ${Build.VERSION.RELEASE}
 App Version: $body
 Device Brand: ${Build.BRAND}
 Device Model: ${Build.MODEL}
 Device Manufacturer: ${Build.MANUFACTURER}"""
            } catch (e: PackageManager.NameNotFoundException) {
            }
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("nextbigdev@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback of android app")
            intent.putExtra(Intent.EXTRA_TEXT, body)
            context.startActivity(
                Intent.createChooser(
                    intent,
                    context.getString(R.string.choose_email_client)
                )
            )
        }
    }
}