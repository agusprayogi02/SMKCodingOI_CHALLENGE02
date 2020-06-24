package id.agusprayogi02.pabarcovid19.repository

import androidx.lifecycle.LiveData
import id.agusprayogi02.pabarcovid19.database.dao.CoronaDao
import id.agusprayogi02.pabarcovid19.item.CovidConfirmedItem

class CoronaRepository(private val coronaDao: CoronaDao) {

    var allCorona: LiveData<List<CovidConfirmedItem>> = coronaDao.getAllCoronas()

    suspend fun insertAll(coronas: List<CovidConfirmedItem>) {
        coronaDao.insertAllCor(coronas)
    }

    suspend fun deleteAll(){
        coronaDao.deleteAll()
    }
}