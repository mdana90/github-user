package com.dana.githubuser

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GithubUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initFlipper()
    }

    private fun initFlipper() {
        if (BuildConfig.DEBUG) {
            SoLoader.init(this, false)
            val client = AndroidFlipperClient.getInstance(this)
            val networkFlipperPlugin = NetworkFlipperPlugin()
            client.addPlugin(networkFlipperPlugin)
            client.start()
        }
    }
}