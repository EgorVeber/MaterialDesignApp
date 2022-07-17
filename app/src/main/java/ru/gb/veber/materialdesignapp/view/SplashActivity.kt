package ru.gb.veber.materialdesignapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors
import kotlinx.android.synthetic.main.activity_splash.view.*
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.ActivitySplashBinding
import ru.gb.veber.materialdesignapp.utils.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        if (DynamicColors.isDynamicColorAvailable()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            setTheme(getThemePrefs())
            setContentView(binding.root)

            binding.root.image_view.animate().rotationBy(1080f).setDuration(4000L).start()

            Handler(mainLooper).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 4000L)
        }
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
}