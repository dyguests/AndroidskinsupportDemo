package com.fanhl.app_zip

import android.app.Application
import com.fanhl.app_zip.skin.ZipSDCardLoader
import skin.support.SkinCompatManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinCompatManager.withoutActivity(this)
            .addStrategy(ZipSDCardLoader)
            .loadSkin()
    }
}
