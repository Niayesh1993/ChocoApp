package com.example.mediasample.app

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
internal class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: MediaApplication): Context = application.applicationContext

}