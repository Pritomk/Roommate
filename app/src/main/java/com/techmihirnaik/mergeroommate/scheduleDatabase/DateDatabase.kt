package com.techmihirnaik.mergeroommate.scheduleDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Date::class), version = 1)
abstract class DateDatabase : RoomDatabase() {

    abstract fun getDateDao(): DateDao

    companion object{
        private  var INSTANCE: DateDatabase?=null
        fun getDatabase(context: Context): DateDatabase {
            if (INSTANCE ==null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context, DateDatabase::class.java,"note_data")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}
