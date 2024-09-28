package com.trainerview.app.app

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppComponentHolder.createComponent(this)
    }
}