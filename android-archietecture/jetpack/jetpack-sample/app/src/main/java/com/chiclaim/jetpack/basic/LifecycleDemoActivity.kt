package com.chiclaim.jetpack.basic

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.chiclaim.jetpack.BaseActivity
import com.chiclaim.jetpack.R

/*
主要基于观察者模式

1，ReportFragment.injectIfNeededIn()
2，LifecycleRegistry 核心类

 */
class LifecycleDemoActivity : BaseActivity() {

    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lifecycle_layout)


        myLocationListener = MyLocationListener(this, lifecycle) { location ->
            // update UI
        }

        Util.checkUserStatus { result ->
            if (result) {
                myLocationListener.enable()
            }
        }

        lifecycle.addObserver(myLocationListener)
    }
}

internal abstract class LocationListener(
        private val context: Context,
        private val lifecycle: Lifecycle,
        private val callback: (Location) -> Unit
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        Log.e("MyLocationListener", "onDestroy...")
    }

}

internal class MyLocationListener(
        context: Context,
        private val lifecycle: Lifecycle,
        callback: (Location) -> Unit) : LocationListener(context, lifecycle, callback) {

    private var enabled = false

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (enabled) {
            // connect
            Log.e("MyLocationListener", "start connect")
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        // disconnect if connected
        Log.e("MyLocationListener", "disconnect")

    }

    fun enable() {
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
            start()
        }
    }

}

internal class Util {
    companion object {
        fun checkUserStatus(callback: (Boolean) -> Unit) {
            Handler().postDelayed({
                callback(true)
            }, 3000)
        }
    }

}