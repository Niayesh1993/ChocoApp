package com.example.choco.di;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class ChocoApplication extends MultiDexApplication {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
