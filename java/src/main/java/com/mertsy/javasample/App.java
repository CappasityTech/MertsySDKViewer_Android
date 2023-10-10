package com.mertsy.javasample;

import android.app.Application;

import com.mertsy.sdk.MertsySDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MertsySDK.init(this, "YOUR TOKEN HERE");
    }

}
