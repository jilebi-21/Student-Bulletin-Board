package com.hm.student.utils

import androidx.appcompat.app.AppCompatDelegate

object Utils {
    fun setTheme(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}