package com.example.mediawarner.ui.main.view

import androidx.lifecycle.ViewModel
import com.example.mediasample.app.ViewModelKey
import com.example.mediasample.ui.main.view.MainActivity
import com.example.mediasample.ui.main.view.MainActivityModule
import com.example.mediasample.ui.main.view.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

//initial activity as a module
@Module(includes = [MainActivityModule::class])
internal abstract class MainActivityBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindsViewModel(viewModel: MainActivityViewModel): ViewModel
}