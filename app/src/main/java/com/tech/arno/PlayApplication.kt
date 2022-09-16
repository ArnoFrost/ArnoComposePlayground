package com.tech.arno

import android.app.Application
import com.blankj.utilcode.util.Utils

class PlayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化
        Utils.init(this)
    }
}