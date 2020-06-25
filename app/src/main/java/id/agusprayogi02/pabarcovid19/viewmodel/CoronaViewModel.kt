package id.agusprayogi02.pabarcovid19.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.agusprayogi02.pabarcovid19.database.AppDatabase
import id.agusprayogi02.pabarcovid19.database.entity.CoronaModel
import id.agusprayogi02.pabarcovid19.repository.CoronaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoronaViewModel() : ViewModel() {

    lateinit var repository: CoronaRepository
    lateinit var allCorona: LiveData<List<CoronaModel>>

    fun init(context: Context) {
        val coronaDao = AppDatabase.getDatabase(context).coronaDao()
        repository = CoronaRepository(coronaDao)
        allCorona = repository.allCorona
    }

    fun insertAll(coronas: List<CoronaModel>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
        repository.insertAll(coronas)
    }
}