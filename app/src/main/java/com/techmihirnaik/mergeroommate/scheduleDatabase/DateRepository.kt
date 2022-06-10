package com.techmihirnaik.mergeroommate.scheduleDatabase

import androidx.lifecycle.LiveData
import com.techmihirnaik.mergeroommate.scheduleDatabase.Date
import com.techmihirnaik.mergeroommate.scheduleDatabase.DateDao


class DateRepository(private val dateDao: DateDao) {
    val allDate: LiveData<List<Date>> = dateDao.getAllDates()

    suspend fun insert(date: Date){
        dateDao.insert(date)
    }

    suspend fun delete() {
        dateDao.delete()
    }
}