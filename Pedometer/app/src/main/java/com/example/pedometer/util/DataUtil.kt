package com.example.pedometer.util

import android.content.Context
import android.provider.Settings
import com.example.pedometer.R
import com.example.pedometer.domain.Period
import com.example.pedometer.domain.WeekGoal
import com.example.pedometer.room.Pedometer
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.math.BigInteger
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

class DataUtil {

    companion object {

        private val TAG = this::class.java.simpleName

        fun getChartDailyXValue(context: Context): List<String> {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("MM/dd")
            val today = sdf.format(cal.time)
            cal.add(Calendar.MONTH, -1)
            val xvalue = arrayListOf<String>()
            while (true) {
                val date = sdf.format(cal.time)
                if (today != date) {
                    xvalue.add(date)
                } else {
                    xvalue.add(context.getString(R.string.text_today))
                    break
                }
                cal.add(Calendar.DAY_OF_MONTH, 1)
            }

            return xvalue
        }

        fun getChartWeekPeriods(): List<Period> {
            val sdf = SimpleDateFormat("MM/dd")
            val c = Calendar.getInstance()
            val longToday = c.timeInMillis

            c.set(Calendar.DAY_OF_WEEK, 2)    // monday
            val strThisMon = sdf.format(c.time)

            c.add(Calendar.MONTH, -3) // 3 month ago
            c.set(Calendar.DAY_OF_WEEK, 2)    // monday
            c.set(Calendar.HOUR_OF_DAY, 0)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)
            val periods = arrayListOf<Period>()
            while (true) {
                val strMon = sdf.format(c.time)
                val longMon = c.timeInMillis
                c.add(Calendar.DAY_OF_MONTH, 6)
                val strSun = sdf.format(c.time)
                val longSun = c.timeInMillis

                if (strThisMon != strMon) {
                    periods.add(Period("$strMon~$strSun", longMon, longSun))
                } else if (longToday < longMon) break
                else {
                    periods.add(Period("$strThisMon~", longMon, 0))
                    break
                }
                c.add(Calendar.DAY_OF_MONTH, 1)
            }
            return periods
        }

        fun getChartDailyDataSet(
            xvalues: List<String>,
            list: List<Pedometer>,
            context: Context
        ): BarDataSet {
            val barEntries = arrayListOf<BarEntry>()
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("MM/dd")
            for (i in xvalues.indices) {
                val xvalue =
                    if (xvalues[i] == context.getString(R.string.text_today)) DateUtil.getToday() else xvalues[i]
                val filter = list.filter { item ->
                    c.timeInMillis = item.timestamp
                    xvalue == sdf.format(c.time)
                }
                if (filter.isNotEmpty()) {
                    barEntries.add(
                        BarEntry(
                            i.toFloat(),
                            RoomDBUtil.computeSteps(filter[0]).toFloat()
                        )
                    )
                } else {
                    barEntries.add(BarEntry(i.toFloat(), 0f))
                }
            }
            return BarDataSet(barEntries, context.getString(R.string.text_bar_chart))
        }

        fun getChartWeekDataSet(
            periods: List<Period>,
            list: List<Pedometer>,
            context: Context
        ): BarDataSet {
            val barEntries = arrayListOf<BarEntry>()
            for (i in periods.indices) {
                val item = periods[i]
                // past week
                if (item.sunday != 0L) {
                    val filter =
                        list.filter { pedometer -> item.monday <= pedometer.timestamp && pedometer.timestamp <= item.sunday }

                    barEntries.add(
                        BarEntry(
                            i.toFloat(),
                            (RoomDBUtil.computeSumSteps(filter)).toFloat()
                        )
                    )
                }
                // this week
                else {
                    val filter = list.filter { pedometer -> item.monday <= pedometer.timestamp }
                    barEntries.add(
                        BarEntry(
                            i.toFloat(),
                            (RoomDBUtil.computeSumSteps(filter)).toFloat()
                        )
                    )
                }
            }
            return BarDataSet(barEntries, context.getString(R.string.text_bar_chart))
        }

        fun getDataWeekGoal(list: List<Pedometer>, context: Context): List<WeekGoal> {
            val sdf = SimpleDateFormat("yyyyMMdd")
            val c = Calendar.getInstance()
            // Calendar Instance System : Sun ~ Mon
            // Custom System : Mon ~ Sun
            if (c.get(Calendar.DAY_OF_WEEK) == 1) c.add(Calendar.WEEK_OF_YEAR, -1)
            c.set(Calendar.DAY_OF_WEEK, 2)    // monday
            c.set(Calendar.HOUR_OF_DAY, 0)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)
            val longMon = c.timeInMillis
            c.add(Calendar.DAY_OF_MONTH, 6)
            val longSun = c.timeInMillis
            val filter =
                list.filter { item -> item.timestamp in longMon..longSun }.sortedBy { it.timestamp }
            val goal = if (context.getSharedPreferences(
                    context.getString(R.string.text_goal),
                    Context.MODE_PRIVATE
                ) != null
            ) {
                context.getSharedPreferences(
                    context.getString(R.string.text_goal),
                    Context.MODE_PRIVATE
                )!!
                    .getInt(context.getString(R.string.text_goal), DEFAULT_GOAL)
            } else {
                DEFAULT_GOAL
            }

            val result = arrayListOf(
                WeekGoal(-1, context.getString(R.string.text_mon)),
                WeekGoal(-1, context.getString(R.string.text_tue)),
                WeekGoal(-1, context.getString(R.string.text_wed)),
                WeekGoal(-1, context.getString(R.string.text_thu)),
                WeekGoal(-1, context.getString(R.string.text_fri)),
                WeekGoal(-1, context.getString(R.string.text_sat)),
                WeekGoal(-1, context.getString(R.string.text_sun)),
            )
            // Calendar Instance System : Sun ~ Mon
            // Custom System : Mon ~ Sun
            if (c.get(Calendar.DAY_OF_WEEK) == 1) c.add(Calendar.WEEK_OF_YEAR, -1)
            c.set(Calendar.DAY_OF_WEEK, 2)    // monday of this week
            for (i in result.indices) {
                val time = c.timeInMillis
                val f = filter.filter { item -> item.timestamp == time }
                if (f.isNotEmpty()) {
                    val steps = RoomDBUtil.computeSteps(f[0])
                    if (steps >= goal) {
                        result[i].status = STATUS_REACHED    // over than goal
                    } else if (steps > 0) {
                        result[i].status = STATUS_RUN    // less than goal, but not 0
                    } else {
                        result[i].status = STATUS_NOT_YET    // 0
                    }
                } else {
                    result[i].status = STATUS_NOT_YET    // no steps
                }
                c.add(Calendar.DAY_OF_MONTH, 1)
            }
            return result
        }

        fun getRebootBeforeSteps(context: Context): Int {
            val pref = context.getSharedPreferences(TEXT_REBOOT, Context.MODE_PRIVATE)
            return pref.getInt(TEXT_REBOOT_BEFORE_STEPS, 0)
        }

        fun getRebootAfterSteps(
            json: String,
            rebootHour: String,
            currentHour: String,
            context: Context
        ): Int {
            val steps = RoomDBUtil.fromStepsJson(json)
            val filter =
                steps.filter { item -> rebootHour.toInt() <= item.hour.toInt() && item.hour.toInt() <= currentHour.toInt() }
                    .sortedBy { item -> item.hour.toInt() }
            val rebootBeforeSteps = getRebootBeforeSteps(context)
            // reboot after steps = sum(reboot after steps) - reboot before steps
            var sum = 0
            for (item in filter) {
                sum += item.steps
            }
            val rebootAfterSteps = sum - rebootBeforeSteps
            return if (rebootAfterSteps > 0) {
                rebootAfterSteps
            } else {
                0
            }
        }

        fun getDeviceUUID(context: Context): UUID {
            val androidId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            return UUID(androidId.hashCode().toLong(), 0)
        }

        fun convertUUIDToString(uuid: UUID, length: Int): String {
            // UUID to byte array
            val uuidBytes = ByteBuffer.wrap(ByteArray(16)).apply {
                putLong(uuid.mostSignificantBits)
                putLong(uuid.leastSignificantBits)
            }.array()

            // use hash function for sha-256
            val md = MessageDigest.getInstance("SHA-256")
            val hashBytes = md.digest(uuidBytes)

            // hash value to hex value
            val hashBigInt = BigInteger(1, hashBytes)
            var hashStr = hashBigInt.toString(16)

            // hex value to string (substring)
            val resultStr = StringBuilder()
            val step = hashStr.length / length
            for (i in 0 until length) {
                resultStr.append(hashStr[i * step])
            }

            return resultStr.toString()
        }

        fun increaseHexString(value: String, initLength: Int): String {
            var result = value
            if (result.length == initLength) {
                result += "0"
            } else {
                if (result[result.length - 1] == 'F') {
                    result += "0"
                } else if (result[result.length - 1] == '9') {
                    result = result.substring(0, result.length - 1) + "A"
                } else {
                    result = result.substring(
                        0,
                        result.length - 1
                    ) + (result[result.length - 1] + 1).toString()
                }
            }
            return result
        }
    }
}