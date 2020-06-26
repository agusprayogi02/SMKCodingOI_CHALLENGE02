package id.agusprayogi02.pabarcovid19.repository

import androidx.lifecycle.LiveData
import id.agusprayogi02.pabarcovid19.database.dao.NewsDao
import id.agusprayogi02.pabarcovid19.database.entity.NewsModel

class NewsRepository(private val newsDao: NewsDao) {

    var allNews:LiveData<List<NewsModel>> = newsDao.getAllNews()

    suspend fun insertAll(news: List<NewsModel>){
        newsDao.insertAll(news)
    }

    suspend fun deleteAll(){
        newsDao.deleteAll()
    }
}