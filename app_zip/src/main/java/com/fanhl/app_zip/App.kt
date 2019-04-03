package com.fanhl.app_zip

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // SkinCompatManager.withoutActivity(this)
        //     .addStrategy(ZipSDCardLoader)
        //     .loadSkin()
    }
}
