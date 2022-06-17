package ru.gb.veber.materialdesignapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.utils.FILE_SETTINGS
import ru.gb.veber.materialdesignapp.utils.KEY_THEME

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getThemePrefs())
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureFragment.newInstance()).commit()
        }

    }

    private fun getThemePrefs(): Int {
        return when (getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE).getInt(KEY_THEME, 0)) {
            1 -> R.style.MyThemeBlue
            2 -> R.style.MyThemeGreen
            else -> R.style.MyThemeBaseTeal
        }
    }
}