package ru.gb.veber.materialdesignapp.view

import SelectThemeFragment
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.ActivityMainBinding
import ru.gb.veber.materialdesignapp.utils.*
import ru.gb.veber.materialdesignapp.view.old.PictureFragment
import ru.gb.veber.materialdesignapp.view.pictureDay.PictureDayMainFragment
import ru.gb.veber.materialdesignapp.view.planets.PlanetsMainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNightMode()
        setTheme(getThemePrefs())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_bottom_view_picture -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureDayMainFragment.newInstance())
                        .addToBackStack("null").commit()
                    true
                }
                R.id.action_bottom_planets -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PlanetsMainFragment.newInstance())
                        .addToBackStack("null").commit()
                    true
                }
                R.id.action_bottom_view1 -> {
                    false
                }
                R.id.action_bottom_wiki -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureFragment.newInstance())
                        .addToBackStack("null").commit()
                    true
                }
                R.id.action_bottom_settings -> {
                    SelectThemeFragment().show(supportFragmentManager, "")
                    false
                }
                else -> {
                    true
                }
            }
        }
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.action_bottom_view_picture
        }
        bottomNavigationView.setOnItemReselectedListener {
            //чтобы Reselected не работал
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //чтобы при пересоздании темы выбюранный был первый
        bottomNavigationView.selectedItemId = R.id.action_bottom_view_picture
    }


    private fun getThemePrefs(): Int {
        return when (getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE).getInt(
            KEY_THEME,
            KEY_THEME_TEAL
        )) {
            KEY_THEME_BLUE -> R.style.MyThemeBlue
            KEY_THEME_GREEN -> R.style.MyThemeGreen
            else -> R.style.MyThemeBaseTeal
        }
    }

    override fun onBackPressed() {
        if (bottomNavigationView.selectedItemId == R.id.action_bottom_view_picture) {
            //super.onBackPressed() fragmentManager.getBackStackEntryCount() == 0
            finish()
        } else {
            bottomNavigationView.selectedItemId = R.id.action_bottom_view_picture
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