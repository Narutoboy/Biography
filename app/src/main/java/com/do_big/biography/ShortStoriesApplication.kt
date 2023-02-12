package com.do_big.biography

import android.app.Application
import android.content.Context

class ShortStoriesApplication : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var applicationAssetLoader: AssetLoader
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        applicationAssetLoader = AssetLoaderImpl(applicationContext.resources.assets)
    }
}
