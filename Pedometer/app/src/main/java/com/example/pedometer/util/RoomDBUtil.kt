package com.example.pedometer.util

import android.util.Log
import androidx.room.PrimaryKey
import com.example.pedometer.retrofit.PedometerSteps
import com.example.pedometer.room.Pedometer
import com.example.pedometer.room.dto.StepsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomDBUtil {
    companion object {
        private val TAG = this::class.java.simpleName

        fun getPrevStepSum(json: String): Int {
            // steps: [{'0010': 23}, {'0020': 30}, {'0030': 15}]
            Log.d(TAG, "steps: $json")
            var result = 0
            val steps = fromStepsJson(json)
            for (step in steps) {
                result += step.steps
            }
            return result
        }


        fun addSteps(json: String, currentHour: String, currentSteps: Int): String {
            val result = arrayListOf<StepsItem>()
            val steps = fromStepsJson(json)
            val filter = steps.filter { step -> step.hour == currentHour }
            // 오늘 해당 시가 있을 경우
            if (filter.isNotEmpty()) {
                for (step in steps) {
                    // 해당 시에 step 추가하는 방식
                    if (step.hour == currentHour) {
                        val newSteps = step.steps + currentSteps
                        val new = StepsItem(step.hour, newSteps)
                        result.add(new)
                    } else {
                        result.add(step)
                    }
                }
            }
            // 오늘 해당 시가 없을 경우
            else {
                result.addAll(steps)
                val current = StepsItem(currentHour, currentSteps)
                result.add(current)
            }
            return Gson().toJson(result)
        }

        fun replaceSteps(json: String, currentHour: String, currentSteps: Int): String {
            val result = arrayListOf<StepsItem>()
            val steps = fromStepsJson(json)
            val filter = steps.filter { step -> step.hour == currentHour }
            // 오늘 해당 시가 있을 경우
            if (filter.isNotEmpty()) {
                for (step in steps) {
                    // 해당 시에 step 추가하는 방식
                    if (step.hour == currentHour) {
                        val new = StepsItem(step.hour, currentSteps)
                        result.add(new)
                    } else {
                        result.add(step)
                    }
                }
            }
            // 오늘 해당 시가 없을 경우
            else {
                result.addAll(steps)
                val current = StepsItem(currentHour, currentSteps)
                result.add(current)
            }
            return Gson().toJson(result)
        }

        fun fromStepsJson(string: String): List<StepsItem> {
            val type = object : TypeToken<List<StepsItem>>() {}.type
            return try {
                Gson().fromJson(string, type)
            } catch (e: Exception) {
                listOf()
            }
        }

        fun fillStepsList(list: List<StepsItem>): List<StepsItem> {
            val new = arrayListOf<StepsItem>()
            for (h in 1..24) {
                val hour = String.format("%02d", h)
                val filter = list.filter { item -> item.hour == hour }
                if (filter.isEmpty()) {
                    new.add(StepsItem(hour, 0))
                } else {
                    new.add(filter[0])
                }
            }
            return new.toList()
        }


        fun computeSteps(item: Pedometer): Int {
            var sum = 0
            val steps = fromStepsJson(item.steps)
            for (step in steps) {
                sum += step.steps
            }
            return sum
        }

        fun computeMaxSteps(list: List<Pedometer>): Int {
            var max = 0
            for (item in list) {
                val value = computeSteps(item)
                if (max < value) {
                    max = value
                }
            }
            return max
        }

        fun computeSumSteps(list: List<Pedometer>): Int {
            var sum = 0
            for (item in list) {
                sum += computeSteps(item)
            }
            return sum
        }

        fun stepsToString(item: Pedometer): String {
            var result = "date: ${DateUtil.convertDate(item.timestamp)} \n"
            result += "initSteps: ${item.initSteps} \n"
            val steps = fromStepsJson(item.steps)
            for (step in steps) {
                result += "${step.hour} : ${step.steps} \n"
            }
            return result
        }

        fun convertPedometerStepsToPedometer(list: List<PedometerSteps>): List<Pedometer> {
            val result = arrayListOf<Pedometer>()
            for (item in list) {
                result.add(
                    Pedometer(
                        0, item.timestamp, item.yyyymmdd, item.initSteps, item.steps
                    )
                )
            }
            return result.toList()
        }

        fun getMaxSteps(item: Pedometer): Int {
            var max = 0
            for (v in fromStepsJson(item.steps)) {
                if (max < v.steps) max = v.steps
            }
            return max
        }
    }
}