package com.example.pedometer.util

import android.content.Context
import android.util.Log
import com.example.pedometer.R
import com.example.pedometer.domain.Period
import com.example.pedometer.domain.WeekGoal
import com.example.pedometer.room.Pedometer
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

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
                } else {
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
                    barEntries.add(BarEntry(i.toFloat(), DBUtil.computeSteps(filter[0]).toFloat()))
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
                            (DBUtil.computeSumSteps(filter)).toFloat()
                        )
                    )
                }
                // this week
                else {
                    val filter = list.filter { pedometer -> item.monday <= pedometer.timestamp }
                    barEntries.add(
                        BarEntry(
                            i.toFloat(),
                            (DBUtil.computeSumSteps(filter)).toFloat()
                        )
                    )
                }
            }
            return BarDataSet(barEntries, context.getString(R.string.text_bar_chart))
        }

        fun getDataWeekGoal(context: Context): List<WeekGoal> {
            return listOf(
                WeekGoal(true, context.getString(R.string.text_sun)),
                WeekGoal(true, context.getString(R.string.text_mon)),
                WeekGoal(false, context.getString(R.string.text_tue)),
                WeekGoal(false, context.getString(R.string.text_wed)),
                WeekGoal(false, context.getString(R.string.text_thu)),
                WeekGoal(false, context.getString(R.string.text_fri)),
                WeekGoal(false, context.getString(R.string.text_sat)),
            )
        }


    }
}