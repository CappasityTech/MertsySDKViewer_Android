package com.mertsy.kotlinsample

import android.app.Application
import com.mertsy.sdk.MertsySDK

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MertsySDK.init(this, "YOUR TOKEN HERE")
    }

}