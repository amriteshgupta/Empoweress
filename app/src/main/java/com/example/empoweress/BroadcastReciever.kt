package com.example.empoweress

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.KeyEvent

class VolumeButtonReceiver : BroadcastReceiver() {
    companion object {
        private var countVolumeUp = 0
        private var lastPressTime: Long = 0
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_MEDIA_BUTTON) {
            val keyEvent = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
            if (keyEvent?.action == KeyEvent.ACTION_DOWN) {
                val pressTime = System.currentTimeMillis()
                if (pressTime - lastPressTime < 1000) {
                    countVolumeUp++
                } else {
                    countVolumeUp = 1
                }
                lastPressTime = pressTime

                if (countVolumeUp == 5) {
                    val i = Intent(context, MainActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context?.startActivity(i)
                    countVolumeUp = 0
                }
            }
        }
    }
}
