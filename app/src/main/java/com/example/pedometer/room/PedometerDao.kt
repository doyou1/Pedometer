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

    @Query("SELECT count(*) FROM Pedometer WHERE date = :date")
    fun existByDate(date: Long): Int

    @Query("SELECT * FROM Pedometer WHERE date = :date")
    fun getByDate(date: Long): Pedometer

    @Query("SELECT * FROM Pedometer")
    fun getAll() : List<Pedometer>
}