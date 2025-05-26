package com.example.lab4_skill

import android.app.Application
import com.example.lab4_skill.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SkillTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SkillTrackerApplication)
            modules(appModule)
        }
    }
}