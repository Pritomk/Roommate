package com.techmihirnaik.mergeroommate.schedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.techmihirnaik.mergeroommate.scheduleDatabase.Date
import com.techmihirnaik.mergeroommate.scheduleDatabase.DateDatabase
import com.techmihirnaik.mergeroommate.scheduleDatabase.DateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DateViewModel(application: Application): AndroidViewModel(application) {
    var  repository: DateRepository
    val allDates : LiveData<List<Date>>

    init {
        val dao = DateDatabase.getDatabase(application).getDateDao()
        repository = DateRepository(dao)
        allDates = repository.allDate
    }

    fun insert(date:Date){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(date)
        }
    }

    fun delete(){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete()
        }
    }
}