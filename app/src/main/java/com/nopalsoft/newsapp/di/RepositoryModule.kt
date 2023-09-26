package com.nopalsoft.newsapp.di

import com.nopalsoft.newsapp.model.NewsDao
import com.nopalsoft.newsapp.provider.NewsProvider
import com.nopalsoft.newsapp.repository.NewsRepository
import com.nopalsoft.newsapp.repository.NewsRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providerNewsRepository(provider: NewsProvider, dao: NewsDao): NewsRepository =
        NewsRepositoryImp(provider,dao)
}








