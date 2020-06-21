package id.agusprayogi02.pabarcovid19.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import id.agusprayogi02.pabarcovid19.database.entity.UsersModel

@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    fun getAllUsers():LiveData<List<UsersModel>>
}