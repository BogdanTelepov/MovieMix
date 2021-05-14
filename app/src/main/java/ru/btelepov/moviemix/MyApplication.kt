package ru.btelepov.moviemix

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        lateinit var instance: MyApplication
        fun getContext(): Context? {
            return instance.applicationContext
        }
    }
}