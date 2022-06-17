package ru.gb.veber.materialdesignapp.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.utils.FILE_SETTINGS
import ru.gb.veber.materialdesignapp.utils.KEY_MODE_DARK
import ru.gb.veber.materialdesignapp.utils.KEY_THEME

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getThemePrefs())
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureFragment.newInstance()).commit()
            setNightMode()// работает плохо пока так оставляем
        }
    }

    private fun getThemePrefs(): Int {
        Log.d("TAG", "getThemePrefs() called")
        return when (getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE).getInt(KEY_THEME, 0)) {
            1 -> R.style.MyThemeBlue
            2 -> R.style.MyThemeGreen
            else -> R.style.MyThemeBaseTeal
        }
    }

    private fun setNightMode() {
        if (getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE).getBoolean(KEY_MODE_DARK, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}