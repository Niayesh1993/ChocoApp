package com.example.choco.di

import com.example.choco.data.repository.DataRepository
import com.example.choco.data.repository.DataSource
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
    fun provideDataRepository(dataSource: DataSource): DataRepository {
        return DataRepository.getInstance(dataSource)
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