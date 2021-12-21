package com.hm.student.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.hm.student.R
import com.hm.student.ui.login.LoginActivity
import com.hm.student.utils.Utils

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDivider(null)

        findPreference<Preference>("settings_about")?.setOnPreferenceClickListener {
            val dialogs = AlertDialog.Builder(requireContext())
                .setView(R.layout.info_layout)
                .setNegativeButton("Cancel") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                .create()

            dialogs.show()
            true
        }

        findPreference<Preference>("settings_signout")?.setOnPreferenceClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
            true
        }

    }

    override fun onSharedPreferenceChanged(sp: SharedPreferences?, key: String?) {
        when (key) {
            "settings_app_theme" -> {
                sp?.getString("settings_app_theme", "-1")?.let {
                    Utils.setTheme(it.toInt())
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .unregisterOnSharedPreferenceChangeListener(this)
    }
}