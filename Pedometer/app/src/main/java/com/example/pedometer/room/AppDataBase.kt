package com.example.pedometer.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Pedometer::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pedometerDao(): PedometerDao
}