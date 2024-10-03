package com.sample.toyotacomic.injection

import com.sample.toyotacomic.service.ComicApiService
import com.sample.toyotacomic.repsoitiry.ComicRepository
import com.sample.toyotacomic.repsoitiry.ComicRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideComicApiService(): ComicApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://xkcd.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ComicApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideComicRepository(comicApiService: ComicApiService): ComicRepository {
        return ComicRepositoryImpl(comicApiService)
    }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


}