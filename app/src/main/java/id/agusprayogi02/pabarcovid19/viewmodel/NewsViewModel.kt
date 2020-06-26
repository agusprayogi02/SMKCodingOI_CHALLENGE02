package id.agusprayogi02.pabarcovid19.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.agusprayogi02.pabarcovid19.database.AppDatabase
import id.agusprayogi02.pabarcovid19.database.entity.NewsModel
import id.agusprayogi02.pabarcovid19.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel() : ViewModel() {

    lateinit var newsAll: LiveData<List<NewsModel>>
    lateinit var repo: NewsRepository

    fun init(context: Context) {
        val dao = AppDatabase.getDatabase(context).newsDao()
        repo = NewsRepository(dao)
        newsAll = repo.allNews
    }

    fun insertAll(news: List<NewsModel>) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
        repo.insertAll(news)
    }
}