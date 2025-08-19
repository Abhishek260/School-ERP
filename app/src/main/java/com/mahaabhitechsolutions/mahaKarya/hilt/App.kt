package com.mahaabhitechsolutions.mahaKarya.hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App:Application() {

    companion object {
        private lateinit var app: App
        var BT_NAME: String = "Bluetooth Not Connected."

        fun get(): App {
            return app
        }
    }
}