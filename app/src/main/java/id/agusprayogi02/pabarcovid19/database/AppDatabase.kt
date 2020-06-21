package id.agusprayogi02.pabarcovid19.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.agusprayogi02.pabarcovid19.database.dao.UsersDao
import id.agusprayogi02.pabarcovid19.database.entity.UsersModel

@Database(entities = [UsersModel::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun Users(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val temInstance = INSTANCE
            if (temInstance != null) {
                return temInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"users_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}