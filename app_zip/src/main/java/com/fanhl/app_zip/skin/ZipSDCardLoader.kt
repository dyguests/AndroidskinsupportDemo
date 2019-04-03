package com.fanhl.app_zip.skin

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import skin.support.SkinCompatManager
import skin.support.load.SkinSDCardLoader
import skin.support.utils.SkinFileUtils
import java.io.File
import java.util.HashMap

object ZipSDCardLoader : SkinSDCardLoader() {
    /**简单的缓存每次读到的图片*/
    var imgs = HashMap<Int, Drawable>()
    var colors: Map<String, String> = HashMap()

    override fun getType(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSkinPath(context: Context?, skinName: String?): String {
        return File(SkinFileUtils.getSkinDir(context), skinName).absolutePath
    }

}
