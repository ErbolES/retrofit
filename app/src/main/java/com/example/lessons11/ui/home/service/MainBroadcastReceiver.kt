package com.example.lessons11.ui.home.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MainBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        StringBuilder().apply {
            append("СООБЩЕНИЕ ОТ СИСТЕМЫ\n")
            append("Action: ${p1?.action}")
            toString().also {
                Toast.makeText(p0, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}