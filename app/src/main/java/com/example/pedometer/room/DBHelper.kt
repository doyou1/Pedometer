package com.example.pedometer.room

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.pedometer.room.dto.Steps
import com.example.pedometer.util.DBUtil
import com.example.pedometer.util.DateUtil
import com.example.pedometer.util.TEXT_PEDOMETER
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DBHelper {
    companion object {
        private val TAG = this::class.java.simpleName

        fun process(context: Context, steps: Int) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                TEXT_PEDOMETER
            ).build().pedometerDao()

            GlobalScope.launch(Dispatchers.IO) {
                // 오늘에 대한 레코드 있는지 확인
                val result = db.existByDate(DateUtil.getCurrentDate())
                // 레코드 없으면
                if (result == 0) {
                    // insert
                    val pedometer = Pedometer(
                        0,
                        DateUtil.getCurrentDate(),
                        steps,
                        Gson().toJson(Steps(listOf()))
                    )
                    db.insert(pedometer)
                }
                // 레코드 있으면
                else {
                    val item = db.getByDate(DateUtil.getCurrentDate())
                    Log.d(TAG, "steps: ${item.steps}")
                    val prevStepSum = DBUtil.getPrevStepSum(item.steps)
                    val currentSteps = steps - (item.initSteps + prevStepSum)
                    val newSteps = DBUtil.addSteps(item.steps, DateUtil.getCurrentHour(), currentSteps)
                    item.steps = newSteps
                    db.update(item)
                }
            }
        }

        suspend fun getCurrent(context: Context): Pedometer? = withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                TEXT_PEDOMETER
            ).build().pedometerDao()
            return@withContext db.getByDate(DateUtil.getCurrentDate())
        }

        suspend fun getAll(context: Context) : List<Pedometer> = withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                TEXT_PEDOMETER
            ).build().pedometerDao()
            return@withContext db.getAll()
        }
    }
}