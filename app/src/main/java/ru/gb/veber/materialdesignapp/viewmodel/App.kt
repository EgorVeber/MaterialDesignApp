package ru.gb.veber.materialdesignapp.viewmodel

import android.app.Application
import com.google.android.material.color.DynamicColors

class App : Application() {
    companion object {
        var appInstance: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        if (DynamicColors.isDynamicColorAvailable()) {
            DynamicColors.applyToActivitiesIfAvailable(this)
        }
    }
}