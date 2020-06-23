package id.agusprayogi02.pabarcovid19.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.agusprayogi02.pabarcovid19.database.AppDatabase
import id.agusprayogi02.pabarcovid19.database.entity.PeriksaModel
import id.agusprayogi02.pabarcovid19.repository.PeriksaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PeriksaViewModel() : ViewModel() {

    lateinit var repo: PeriksaRepository
    lateinit var allPeriksa: LiveData<List<PeriksaModel>>

    fun init(context: Context) {
        val periksaDao = AppDatabase.getDatabase(context).periksaDao()
        repo = PeriksaRepository(periksaDao)
        allPeriksa = repo.allPeriksa
    }

    fun delete(periksa: PeriksaModel) = viewModelScope.launch(Dispatchers.IO) {
        repo.delete(periksa)
    }

    fun insertAll(periksas: List<PeriksaModel>) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
        repo.insertAll(periksas)
    }

    fun addData(periksa: PeriksaModel) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(periksa)
    }
}