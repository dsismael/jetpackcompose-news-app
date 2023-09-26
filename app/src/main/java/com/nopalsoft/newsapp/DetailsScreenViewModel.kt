package com.nopalsoft.newsapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nopalsoft.newsapp.model.News
import com.nopalsoft.newsapp.model.NewsDao
import com.nopalsoft.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news

    fun getNewsByTitle(title: String){
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNew(title)
            _news.postValue(news)
        }
    }

    fun saveNotice(news: News){
        val favValue = news.isFavorite
        val updateNews = news.copy(isFavorite = !favValue)

        viewModelScope.launch {
            if (updateNews.isFavorite){
                repository.addFavNew(updateNews)
            }else{
                repository.deleteFavNew(updateNews)
            }
        }
    }
}