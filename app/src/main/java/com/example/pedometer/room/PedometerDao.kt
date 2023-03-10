package com.example.pedometer.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PedometerDao {

    @Insert
    fun insert(item: Pedometer)

    @Update
    fun update(item: Pedometer)

    @Query("SELECT count(*) FROM Pedometer WHERE timestamp = :date")
    fun existByDate(date: Long): Int

    @Query("SELECT * FROM Pedometer WHERE timestamp = :date")
    fun getByDate(date: Long): Pedometer

    @Query("SELECT * FROM Pedometer WHERE :start <= timestamp and timestamp <= :end order by timestamp")
    fun getDailyByDate(start: Long, end: Long): List<Pedometer>

    @Query("SELECT * FROM Pedometer WHERE :start <= timestamp and timestamp <= :end order by timestamp")
    fun getWeekByDate(start: Long, end: Long): List<Pedometer>

    @Query("SELECT * FROM Pedometer")
    fun getAll() : List<Pedometer>
}