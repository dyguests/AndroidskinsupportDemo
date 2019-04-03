package com.fanhl.app_zip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fanhl.app_zip.skin.SkinLoader
import kotlinx.android.synthetic.main.activity_main.*
import skin.support.SkinCompatManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_skin_default.setOnClickListener { SkinCompatManager.getInstance().restoreDefaultTheme() }
        btn_skin_red.setOnClickListener {
            SkinCompatManager.getInstance().loadSkin("red", SkinLoader.SKIN_LOADER_STRATEGY_ZIP)
        }
    }
}
