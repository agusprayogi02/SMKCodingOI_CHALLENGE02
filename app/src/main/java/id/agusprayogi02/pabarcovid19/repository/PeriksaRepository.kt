package id.agusprayogi02.pabarcovid19.repository

import androidx.lifecycle.LiveData
import id.agusprayogi02.pabarcovid19.database.dao.PeriksaDao
import id.agusprayogi02.pabarcovid19.database.entity.PeriksaModel

class PeriksaRepository(private val periksaDao: PeriksaDao) {

    val allPeriksa: LiveData<List<PeriksaModel>> = periksaDao.getAllPer()

    suspend fun insert(periksa: PeriksaModel){
        periksaDao.insertPer(periksa)
    }

    suspend fun insertAll(periksas: List<PeriksaModel>){
        periksaDao.insertAllPer(periksas)
    }

    suspend fun delete(periksa: PeriksaModel){
        periksaDao.deletePer(periksa)
    }

    suspend fun deleteAll(){
        periksaDao.deleteAllPer()
    }
}