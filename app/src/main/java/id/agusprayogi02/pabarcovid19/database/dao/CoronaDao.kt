package id.agusprayogi02.pabarcovid19.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.agusprayogi02.pabarcovid19.item.CovidConfirmedItem

@Dao
interface CoronaDao {

    @Query("SELECT * FROM corona")
    fun getAllCoronas(): LiveData<List<CovidConfirmedItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCor(coronas: List<CovidConfirmedItem>)

    @Query("DELETE FROM corona")
    suspend fun deleteAll()
}