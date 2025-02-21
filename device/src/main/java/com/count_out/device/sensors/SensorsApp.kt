package com.count_out.device.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import javax.inject.Inject

class SensorsApp @Inject constructor( val context: Context): SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepCount = 0

    fun onCreate(){
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL)
    }
    fun onPause() {
        sensorManager.unregisterListener(this)
    }
    fun onResume() {
        val stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) {
            stepCount++
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {    }
}