package com.techmihirnaik.mergeroommate.scheduleDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Date")
data class Date(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "cInDate")
    val cInDate: String,
    @ColumnInfo(name = "cOutDate")
    val cOutDate:String,
    @ColumnInfo(name = "cInDate_1")
    val cInDate_1: String,
    @ColumnInfo(name = "cOutDate_2")
    val cOutDate_2:String
)