package com.hm.student.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.hm.student.R
import com.hm.student.fragments.SettingsFragment
import com.hm.student.model.UserModel

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val settingsFragment = SettingsFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, settingsFragment)
            .commit()
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        findViewById<TextView>(R.id.user_type).text = sp.getString("type", "")
        findViewById<TextView>(R.id.user_name).text = sp.getString("name", "")
        findViewById<TextView>(R.id.user_email).text = sp.getString("email", "")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return false
    }

}