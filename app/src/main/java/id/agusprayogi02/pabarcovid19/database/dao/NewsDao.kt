package id.agusprayogi02.pabarcovid19.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.agusprayogi02.pabarcovid19.database.entity.NewsModel

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAllNews(): LiveData<List<NewsModel>>

    @Query("DELETE FROM news")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsModel>)

}