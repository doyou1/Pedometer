package com.example.pedometer.room

import androidx.room.*

@Dao
interface PedometerDao {

    @Insert
    fun insert(item: Pedometer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Pedometer>)

    @Delete
    fun deleteAll(list: List<Pedometer>)

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
    fun getAll(): List<Pedometer>
}