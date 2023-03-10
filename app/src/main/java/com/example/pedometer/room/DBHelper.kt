package com.example.pedometer.room

import android.content.Context
import androidx.room.Room
import com.example.pedometer.room.dto.Steps
import com.example.pedometer.util.DBUtil
import com.example.pedometer.util.DataUtil
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
                val result = db.existByDate(DateUtil.getCurrentTimestamp())
                // 레코드 없으면
                if (result == 0) {
                    // insert
                    val pedometer = Pedometer(
                        0,
                        DateUtil.getCurrentTimestamp(),
                        DateUtil.getFullToday(),
                        steps,
                        Gson().toJson(Steps(listOf()))
                    )
                    db.insert(pedometer)
                }
                // 레코드 있으면
                else {
                    val item = db.getByDate(DateUtil.getCurrentTimestamp())
                    // is reboot today? (step_counter: 0)
                    if (item.initSteps == 0 && DateUtil.isRebootToday(context)) {
                        val currentHour = DateUtil.getCurrentHour()
                        val rebootHour = DateUtil.getRebootHour(context)
                        // current hour == reboot hour
                        if (currentHour.toInt() == rebootHour.toInt()) {
                            // update to before steps + current steps
                            val currentSteps = DataUtil.getRebootBeforeSteps(context) + steps
                            // temp process for minus steps error
                            if(currentSteps < 0) return@launch
                            val newSteps =
                                DBUtil.replaceSteps(item.steps, currentHour, currentSteps)
                            item.steps = newSteps
                            db.update(item)
                        }
                        // current hour > reboot hour
                        else if (currentHour.toInt() > rebootHour.toInt()) {
                            val afterSteps = DataUtil.getRebootAfterSteps(
                                item.steps,
                                rebootHour,
                                currentHour,
                                context
                            )
                            // currentSteps - after reboot step
                            val currentSteps = steps - afterSteps
                            // temp process for minus steps error
                            if(currentSteps < 0) return@launch
                            val newSteps =
                                DBUtil.addSteps(item.steps, currentHour, currentSteps)
                            item.steps = newSteps
                            db.update(item)
                        }
                    } else {
                        val prevStepSum = DBUtil.getPrevStepSum(item.steps)
                        val currentSteps = steps - (item.initSteps + prevStepSum)
                        // temp process for minus steps error
                        if(currentSteps < 0) return@launch
                        val newSteps =
                            DBUtil.addSteps(item.steps, DateUtil.getCurrentHour(), currentSteps)
                        item.steps = newSteps
                        db.update(item)
                    }
                }
            }
        }

        suspend fun update(item: Pedometer, context: Context) = withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                TEXT_PEDOMETER
            ).build().pedometerDao()
            db.update(item)
        }

        suspend fun insert(item: Pedometer, context: Context) = withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                TEXT_PEDOMETER
            ).build().pedometerDao()
            db.insert(item)
        }

        suspend fun getCurrent(context: Context): Pedometer? = withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                TEXT_PEDOMETER
            ).build().pedometerDao()
            return@withContext db.getByDate(DateUtil.getCurrentTimestamp())
        }

        suspend fun getCurrentDaily(context: Context): List<Pedometer> =
            withContext(Dispatchers.IO) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    TEXT_PEDOMETER
                ).build().pedometerDao()
                return@withContext db.getDailyByDate(
                    DateUtil.getOneMonthAgoDate(),
                    DateUtil.getCurrentTimestamp()
                )
            }

        suspend fun getCurrentWeek(context: Context): List<Pedometer> =
            withContext(Dispatchers.IO) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    TEXT_PEDOMETER
                ).build().pedometerDao()
                return@withContext db.getWeekByDate(
                    DateUtil.getThreeMonthAgoDate(),
                    DateUtil.getCurrentTimestamp()
                )
            }

        suspend fun getAll(context: Context): List<Pedometer> = withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                TEXT_PEDOMETER
            ).build().pedometerDao()
            return@withContext db.getAll()
        }
    }
}