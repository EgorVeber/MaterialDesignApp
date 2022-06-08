package ru.gb.veber.materialdesignapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gb.veber.materialdesignapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureFragment.newInstance()).commit()
        }
    }
}