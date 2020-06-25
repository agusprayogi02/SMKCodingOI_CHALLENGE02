package id.agusprayogi02.pabarcovid19.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.agusprayogi02.pabarcovid19.database.AppDatabase
import id.agusprayogi02.pabarcovid19.database.entity.CoronaModel
import id.agusprayogi02.pabarcovid19.repository.CoronaRepository
import id.agusprayogi02.pabarcovid19.session.CountryData
import id.agusprayogi02.pabarcovid19.util.tampilToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoronaViewModel() : ViewModel() {

    lateinit var repository: CoronaRepository
    lateinit var allCoronaSem: LiveData<List<CoronaModel>>
    lateinit var allCoronaMen: LiveData<List<CoronaModel>>
    lateinit var allCoronaKon: LiveData<List<CoronaModel>>

    fun init(context: Context) {
        val coronaDao = AppDatabase.getDatabase(context).coronaDao()
        repository = CoronaRepository(coronaDao)
        allCoronaKon = repository.allCoronaKon
        allCoronaMen = repository.allCoronaMen
        allCoronaSem = repository.allCoronaSem
    }

    fun insertAll(coronas: List<CoronaModel>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
        repository.insertAll(coronas)
    }
}