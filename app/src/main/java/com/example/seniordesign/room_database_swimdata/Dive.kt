package com.example.seniordesign.room_database_swimdata

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo


@Entity(tableName = "dive_table")
class Dive(@PrimaryKey (autoGenerate = true) val id : Int = 0,
           @ColumnInfo(name = "dive_num")       val dive_num: String,
           @ColumnInfo(name = "dive_time")      val dive_time: String,
           @ColumnInfo(name = "dive_pace")      val dive_pace: String,
           @ColumnInfo(name = "dive_distance")  val dive_distance: String,
           @ColumnInfo(name = "dive_date")      val dive_date: String,
           @ColumnInfo(name = "dive_length")    val dive_length: String
           )