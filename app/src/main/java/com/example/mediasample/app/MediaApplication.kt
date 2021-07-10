package com.example.mediasample.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

internal class MediaApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}