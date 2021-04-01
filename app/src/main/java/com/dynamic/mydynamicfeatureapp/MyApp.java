package com.dynamic.mydynamicfeatureapp;

import android.app.Application;
import android.content.Context;

import com.google.android.play.core.splitcompat.SplitCompat;

public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SplitCompat.install(this);
    }
}
