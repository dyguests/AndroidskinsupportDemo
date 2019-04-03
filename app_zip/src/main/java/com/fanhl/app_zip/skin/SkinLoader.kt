package com.fanhl.app_zip.skin

import android.content.Context
import android.graphics.drawable.Drawable
import skin.support.load.SkinSDCardLoader
import skin.support.utils.SkinFileUtils
import java.io.File
import java.util.HashMap

object SkinLoader : SkinSDCardLoader() {
    const val SKIN_LOADER_STRATEGY_ZIP = Integer.MAX_VALUE - 1

    /**简单的缓存每次读到的图片*/
    var imgs = HashMap<Int, Drawable>()
    var colors = HashMap<String, String>()

    override fun getType(): Int {
        return SKIN_LOADER_STRATEGY_ZIP
    }

    override fun getSkinPath(context: Context?, skinName: String?): String {
        return File(SkinFileUtils.getSkinDir(context), skinName).absolutePath
    }
}
