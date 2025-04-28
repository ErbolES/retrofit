package com.example.lessons11.ui.home

import android.os.Looper

class MyThread : Thread() {
    override fun run() {
      Looper.prepare()
        Looper.loop()
    }
}