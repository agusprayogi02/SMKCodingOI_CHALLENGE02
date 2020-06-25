package id.agusprayogi02.pabarcovid19.repository

import androidx.lifecycle.LiveData
import id.agusprayogi02.pabarcovid19.database.dao.CoronaDao
import id.agusprayogi02.pabarcovid19.database.entity.CoronaModel

class CoronaRepository(private val coronaDao: CoronaDao) {

    var allCorona: LiveData<List<CoronaModel>> = coronaDao.getAllCoronas()

    suspend fun insertAll(coronas: List<CoronaModel>) {
        coronaDao.insertAllCor(coronas)
    }

    suspend fun deleteAll(){
        coronaDao.deleteAll()
    }
}