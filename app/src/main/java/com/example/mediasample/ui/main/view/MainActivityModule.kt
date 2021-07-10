package com.example.mediasample.ui.main.view
import com.example.mediasample.data.repository.MainActivityRepository
import com.example.mediasample.data.repository.MainActivityRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
internal class MainActivityModule {

    @Provides
    fun provideMainActivityRepository(): MainActivityRepository = MainActivityRepositoryImpl()
}