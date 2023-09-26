package com.nopalsoft.newsapp.di

import android.content.Context
import androidx.room.Room
import com.nopalsoft.newsapp.model.NewsDao
import com.nopalsoft.newsapp.provider.DbNews
import com.nopalsoft.newsapp.provider.NewsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "https://newsapi.org/v2/".toHttpUrl()

    @Provides
    @Singleton
    fun provideRetrofit(@Named("BaseUrl") baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun providerNewsProvider(retrofit: Retrofit): NewsProvider =
        retrofit.create(NewsProvider::class.java)

    @Singleton
    @Provides
    fun dbNews(@ApplicationContext context: Context): DbNews{
        return Room.databaseBuilder(context, DbNews::class.java, "news_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun newsDao(db: DbNews): NewsDao = db.newsDao()
}








