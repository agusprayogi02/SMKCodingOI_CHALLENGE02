package id.agusprayogi02.pabarcovid19.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.agusprayogi02.pabarcovid19.database.entity.PeriksaModel

@Dao
interface PeriksaDao {

    @Query("SELECT * FROM periksa")
    fun getAllPer():LiveData<List<PeriksaModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPer(periksa: PeriksaModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPer(periksa: List<PeriksaModel>)

    @Delete()
    suspend fun deletePer(periksa: PeriksaModel)

    @Query("DELETE FROM periksa")
    suspend fun deleteAllPer()
}