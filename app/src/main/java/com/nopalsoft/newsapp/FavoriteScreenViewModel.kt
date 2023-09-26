package com.nopalsoft.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nopalsoft.newsapp.model.News
import com.nopalsoft.newsapp.model.NewsDao
import com.nopalsoft.newsapp.repository.NewsRepository
import com.nopalsoft.newsapp.repository.NewsRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    init {
        //getFavNews()
    }

    //private val _favNewsList: LiveData<List<News>> = imprepository.favNews
    private val _favNewsList = repository.getFavorites()
    val favNews: StateFlow<List<News>> = _favNewsList.asStateFlow()

    /*private fun getFavNews(){
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getFavorites()
            _favNewsList.postValue(news)
        }
    }*/

}