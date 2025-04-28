package com.example.lessons11.ui.home.service

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
private const val TAG =  "MainServiseTag"
const val MAIN_SERVICE_STRING_EXTRA = "MainServiceExtra"

class MainServise(name:String? = "Main Service"):IntentService(name) {

    override fun onHandleIntent(p0: Intent?) {
        createLog("onHandleIntent " +
                "${p0?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)}")
    }

    override fun onCreate() {
        createLog("onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createLog("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        createLog("onDestroy")
        val intent = Intent("gol itstep")
        intent.putExtra("Msg","txt mesage")
        sendBroadcast(intent)
        super.onDestroy()
    }

    private fun createLog(message:String){
        Log.d(TAG,message)
    }

}
