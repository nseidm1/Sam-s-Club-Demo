package com.noahseidman.samsclub

import android.app.Application
import com.noahseidman.samsclub.di.InjectionsUtils

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        InjectionsUtils.init(this)
    }
}