package com.dana.githubuser

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GithubUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}