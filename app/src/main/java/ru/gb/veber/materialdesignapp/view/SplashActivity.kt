package ru.gb.veber.materialdesignapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setTheme(R.style.MyThemeBlue)
        setContentView(binding.root)
    }
}