package com.example.pedometer.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.pedometer.room.DBHelper
import com.example.pedometer.room.Pedometer
import com.example.pedometer.util.FLAG_HISTORY
import com.example.pedometer.util.FLAG_HOME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseFragment : Fragment(), SensorEventListener {
    private val TAG = this::class.java.simpleName

    open val currentView = FLAG_HOME

    override fun onResume() {
        super.onResume()
        activity?.let { context ->
            setStepCounter(context)
            // init
            updateSteps(context)
        }
    }

    private fun setStepCounter(context: Context) {
        val sm = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 0)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { e ->
            if (e.values[0] > Integer.MAX_VALUE || e.values[0].toInt() == 0) return
            activity?.let { context ->
                DBHelper.process(context, e.values[0].toInt())
                updateSteps(context)
            }
        }
    }

    private fun updateSteps(context: Context) {
        lifecycleScope.launch(Dispatchers.IO) {
            val current = DBHelper.getCurrent(context)
            val currentDaily = DBHelper.getCurrentDaily(context)
            val currentWeek = DBHelper.getCurrentWeek(context)
            lifecycleScope.launch(Dispatchers.Main) {
                when(currentView) {
                    FLAG_HOME -> {
                        updateCurrentSteps(current)
                    }
                    FLAG_HISTORY -> {
                        updateCurrentDaily(currentDaily)
                        updateCurrentWeek(currentWeek)
                    }
                }
            }
        }
    }

    open fun updateCurrentSteps(item: Pedometer?) {

    }
    open fun updateCurrentDaily(item: List<Pedometer>) {

    }
    open fun updateCurrentWeek(item: List<Pedometer>) {

    }

    override fun onPause() {
        super.onPause()
        activity?.let { context ->
            stopStepCounter(context)
        }
    }

    private fun stopStepCounter(context: Context) {
        val sm = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sm.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // won't happen
    }

}