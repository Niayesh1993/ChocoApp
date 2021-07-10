package com.example.mediasample.app

import com.example.mediasample.ui.detail.view.DetailActivityBuilderModule
import com.example.mediawarner.ui.main.view.MainActivityBuilderModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        AppBuilderModule::class,
        MainActivityBuilderModule::class,
        DetailActivityBuilderModule::class

    ]
)
internal interface AppComponent : AndroidInjector<MediaApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<MediaApplication>
}