package com.techmihirnaik.mergeroommate.scheduleDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.techmihirnaik.mergeroommate.scheduleDatabase.Date


@Dao
interface DateDao {

    @Insert
    suspend fun insert(date: Date)


    @Query("DELETE FROM Date")
    suspend fun  delete()

    @Query("SELECT * FROM Date")
    fun getAllDates(): LiveData<List<Date>>
}
