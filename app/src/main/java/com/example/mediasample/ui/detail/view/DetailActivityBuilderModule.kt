package com.example.mediasample.ui.detail.view

import androidx.lifecycle.ViewModel
import com.example.mediasample.app.ViewModelKey
import com.example.mediasample.ui.detail.viewmodel.DetailActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

//initial activity as a Module
@Module
internal abstract class DetailActivityBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun detailActivity(): DetailActivity

    @Binds
    @IntoMap
    @ViewModelKey(DetailActivityViewModel::class)
    internal abstract fun bindsViewModel(viewModel: DetailActivityViewModel): ViewModel
}