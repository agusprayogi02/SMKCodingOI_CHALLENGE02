package id.agusprayogi02.pabarcovid19.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.agusprayogi02.pabarcovid19.database.dao.CoronaDao
import id.agusprayogi02.pabarcovid19.database.dao.PeriksaDao
import id.agusprayogi02.pabarcovid19.database.dao.UsersDao
import id.agusprayogi02.pabarcovid19.database.entity.PeriksaModel
import id.agusprayogi02.pabarcovid19.database.entity.UsersModel
import id.agusprayogi02.pabarcovid19.item.CovidConfirmedItem

@Database(
    entities = [UsersModel::class, PeriksaModel::class, CovidConfirmedItem::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coronaDao(): CoronaDao
    abstract fun periksaDao(): PeriksaDao
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val temInstance = INSTANCE
            if (temInstance != null) {
                return temInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "users_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}