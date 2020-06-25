package id.agusprayogi02.pabarcovid19.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.agusprayogi02.pabarcovid19.database.entity.UsersModel

@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<UsersModel>>

    @Query("SELECT * FROM users WHERE uid = :uid")
    fun getUser(uid: String): LiveData<UsersModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UsersModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(users: List<UsersModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UsersModel)

    @Delete()
    suspend fun deleteUser(user: UsersModel)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}