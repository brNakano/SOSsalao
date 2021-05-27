package com.example.sossalao

import android.app.Application


class SOSsalaoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: SOSsalaoApplication?  = null
        fun getInstance(): SOSsalaoApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configurar application no Android Manifest")
            }
            return appInstance!!
        }
    }


    override fun onTerminate() {
        super.onTerminate()
    }
}