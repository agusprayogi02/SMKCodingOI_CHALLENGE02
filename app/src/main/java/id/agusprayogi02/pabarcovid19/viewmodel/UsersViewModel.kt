package id.agusprayogi02.pabarcovid19.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.agusprayogi02.pabarcovid19.database.AppDatabase
import id.agusprayogi02.pabarcovid19.database.entity.UsersModel
import id.agusprayogi02.pabarcovid19.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel() : ViewModel(){
    lateinit var repository: UsersRepository

    fun init(context: Context){
        val usersDao = AppDatabase.getDatabase(context).usersDao()
        repository = UsersRepository(usersDao)
    }

    fun addData(user:UsersModel) = viewModelScope.launch(Dispatchers.IO){
        repository.insertUsers(user)
    }

    fun insertAll(users:List<UsersModel>) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAllUsers()
        repository.insertAllUsers(users)
    }

    fun updateData(user: UsersModel) = viewModelScope.launch(Dispatchers.IO){
        repository.updateUsers(user)
    }
}