package com.fanhl.app_zip

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fanhl.app_zip.skin.FileUtils
import com.fanhl.app_zip.skin.SkinLoader
import com.fanhl.app_zip.skin.ZipUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import skin.support.SkinCompatManager
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_skin_default.setOnClickListener { SkinCompatManager.getInstance().restoreDefaultTheme() }
        btn_skin_red.setOnClickListener {
            SkinCompatManager.getInstance().loadSkin("red", SkinLoader.SKIN_LOADER_STRATEGY_ZIP)
        }
        loadData()
    }

    val redZip = "red.zip"

    private val appTheme by lazy { application.filesDir.toString() + File.separator + "Skins" }

    private val sDzip by lazy { appTheme + File.separator + redZip }

    private val sDAppTheme by lazy { appTheme + File.separator + "red" }

    val colors by lazy { sDAppTheme + File.separator + "colors.json" }

    /**
     * 为全仅用于测试
     */
    private fun loadData() {
        val appThemeFile = File(appTheme)

        if (!appThemeFile.exists()) {
            appThemeFile.mkdirs()
        }

        copyFilesFassets(this, redZip, sDzip)

        val sDAppThemeFile = File(sDAppTheme)

        if (sDAppThemeFile.exists()) {
            FileUtils.deleteFiles(sDAppThemeFile)
        }

        try {
            ZipUtils.UnZipFolder(sDzip, appTheme)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (!sDAppThemeFile.exists()) {
            Toast.makeText(this, "解压数据包失败", Toast.LENGTH_LONG).show()
        }

        val colorsFile = File(colors)
        if (colorsFile.exists()) {
            val json = resolveFileGetStr(colorsFile)
            if (json != null) {
                val colors: Map<String, String> = Gson().fromJson(json, object : TypeToken<Map<String, String>>() {}.type)
                if (colors.isNotEmpty()) SkinLoader.colors.putAll(colors)
            }
        }
    }

    /**
     * 将APP自带的bar默认主题包从ASS路径解压出来
     *
     * @param context
     * @param oldPath
     * @param newPath
     */
    fun copyFilesFassets(context: Context, oldPath: String, newPath: String) {
        try {

            val fileNames = context.assets.list(oldPath)//获取assets目录下的所有文件及目录名
            if (fileNames!!.size > 0) {//如果是目录
                val file = File(newPath)
                file.mkdirs()//如果文件夹不存在，则递归
                for (fileName in fileNames) {
                    copyFilesFassets(context, "$oldPath/$fileName", "$newPath/$fileName")
                }
            } else {//如果是文件
                val `is` = context.assets.open(oldPath)
                val fos = FileOutputStream(newPath)
                val buffer = ByteArray(1024)
                var byteCount = 0
                byteCount = `is`.read(buffer)
                while ((byteCount) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount)//将读取的输入流写入到输出流
                    byteCount = `is`.read(buffer)
                }
                fos.flush()//刷新缓冲区
                `is`.close()
                fos.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 将文件内容转为string对象
     *
     * @param file
     * @return
     */
    private fun resolveFileGetStr(file: File): String? {
        val length = file.length().toInt()

        val bytes = ByteArray(length)

        var `in`: FileInputStream? = null
        try {
            `in` = FileInputStream(file)
            `in`.read(bytes)
            `in`.close()
        } catch (e: Exception) {
            return null
        }

        return String(bytes)
    }
}
