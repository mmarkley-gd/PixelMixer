package com.virtualtimetours.pixelmixer

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.pixelmixer.BuildConfig
import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker

class PixelMixerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        UnsplashPhotoPicker.init(
            this, // application
            BuildConfig.UNSPLASH_ACCESS_KEY,
            BuildConfig.UNSPLASH_PASSWORD,
        )
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}