package com.example.choco.di

import com.example.choco.data.repository.MainRepository
import com.example.choco.data.repository.DataSource
import com.example.choco.data.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideMainRepository(dataSource: DataSource): MainRepository {
        return MainRepository.getInstance(dataSource)
    }

    @Provides
    fun provideLoginRepository(dataSource: DataSource): LoginRepository {
        return LoginRepository.getInstance(dataSource)
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

//    @Provides
//    fun provideSearchDataSource(apiService: ApiService): ReplacementSearchRepository {
//        return ReplacementSearchRepository(apiService)
//    }

}