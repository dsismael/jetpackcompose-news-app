package com.nopalsoft.newsapp.repository

import androidx.lifecycle.MutableLiveData
import com.nopalsoft.newsapp.model.News
import com.nopalsoft.newsapp.model.NewsDao
import com.nopalsoft.newsapp.provider.NewsProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NewsRepository {
    suspend fun getNews(country: String): Flow<List<News>>
    fun getNew(title: String): News
    fun addFavNew(addNew: News)
    fun deleteFavNew(deleteNew: News)
    fun getFavorites(): MutableStateFlow<List<News>>


    //suspend fun getFavNews(country: String): List<News>
    //fun getFavNew(title: String): News
}

class NewsRepositoryImp @Inject constructor(
    private val newsProvider: NewsProvider,
    private val newsDao: NewsDao
) : NewsRepository {

    private var news = MutableStateFlow<List<News>>(listOf()) //deberia ser estado (StateFlow)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    //val favNews = MutableLiveData<List<News>>()

     suspend fun getOnlineNews(country: String): List<News> {
        val apiResponse = newsProvider.topHeadLines(country).body()
        if (apiResponse?.status == "error") {
            when (apiResponse.code) {
                "apiKeyMissing" -> throw MissingApiKeyException()
                "apiKeyInvalid" -> throw ApiKeyInvalidException()
                else -> throw Exception()
            }
        }
        return apiResponse?.articles ?: emptyList()
    }

    override suspend fun getNews(country: String): Flow<List<News>> {
       return newsDao.getFavoriteNews().combineTransform(flow = flow<List<News>>{emit(getOnlineNews(country))}){
               favoriteNews, apiNews ->
           news.value = apiNews.map{
                favoriteNews.firstOrNull{
                        favoriteNew -> favoriteNew.title == it.title
                }?.copy(isFavorite = true) ?: it
           }
           emit(news.value)
        }
    }

    override fun getNew(title: String): News =
        news.value.first { it.title == title }

    override fun addFavNew(addNew: News){
        coroutineScope.launch(Dispatchers.IO) {
            newsDao.insert(addNew)
        }
    }

    override fun deleteFavNew(deleteNew: News){
        coroutineScope.launch(Dispatchers.IO) {
            newsDao.delete(deleteNew)
        }
    }

    override fun getFavorites(): MutableStateFlow<List<News>> {
        return news
    }
}

class MissingApiKeyException : java.lang.Exception()
class ApiKeyInvalidException : java.lang.Exception()