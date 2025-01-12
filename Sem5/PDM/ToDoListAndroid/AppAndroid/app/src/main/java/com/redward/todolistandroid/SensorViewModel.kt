package com.redward.todolistandroid

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SensorViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {
    private val sensorManager: SensorManager = application.getSystemService(Application.SENSOR_SERVICE) as SensorManager
    private val _tilt = MutableLiveData<FloatArray>()
    val tilt: LiveData<FloatArray> get() = _tilt

    init {
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.let {
            _tilt.value = it
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SensorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SensorViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}