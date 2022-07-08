package ru.gb.veber.materialdesignapp.view

import SelectThemeFragment
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.ActivityMainBinding
import ru.gb.veber.materialdesignapp.utils.*
import ru.gb.veber.materialdesignapp.view.listPicture.ListPictureDayFragment
import ru.gb.veber.materialdesignapp.view.pictureDay.PictureDayMainFragment
import ru.gb.veber.materialdesignapp.view.pictureDaybehavior.BehaviorFragment
import ru.gb.veber.materialdesignapp.view.planets.PlanetsMainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNightMode()
        setTheme(getThemePrefs())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_bottom_view_picture -> {
                    showFragment(PictureDayMainFragment.newInstance(), true)
                    true
                }
                R.id.action_bottom_planets -> {
                    showFragment(PlanetsMainFragment.newInstance(), true)
                    true
                }
                R.id.action_bottom_coordinator -> {
                    showFragment(BehaviorFragment.newInstance(), true)
                    true
                }
                R.id.action_bottom_recycler -> {
                    showFragment(ListPictureDayFragment.newInstance(), true)
                    true
                }
                R.id.action_bottom_settings -> {
                    SelectThemeFragment().show(supportFragmentManager, "")
                    true
                }
                else -> {
                    true
                }
            }
        }
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.action_bottom_coordinator
        }
        binding.bottomNavigationView.setOnItemReselectedListener {
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.bottomNavigationView.selectedItemId = R.id.action_bottom_view_picture
    }

    private fun showFragment(fragment: Fragment, stack: Boolean) {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.slide_in,
            R.anim.slide_out
        )
            .replace(R.id.container, fragment).apply {
                if (stack) addToBackStack("")
            }.commit()
    }

    private fun getThemePrefs(): Int {
        return when (getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE).getInt(
            KEY_THEME,
            KEY_THEME_BLUE
        )) {
            KEY_THEME_BLUE -> R.style.MyThemeBlue
            KEY_THEME_TEAL -> R.style.MyThemeBaseTeal
            KEY_THEME_GREEN -> R.style.MyThemeGreen
            else -> R.style.MyThemeBlue
        }
    }

    override fun onBackPressed() {
        if (binding.bottomNavigationView.selectedItemId == R.id.action_bottom_view_picture) {
            finish()
        } else {
            binding.bottomNavigationView.selectedItemId = R.id.action_bottom_view_picture
        }
    }

    private fun setNightMode() {
        if (getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE).getBoolean(KEY_MODE_DARK, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}