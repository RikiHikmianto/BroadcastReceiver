package co.id.rikihikmianto.mybroadcastreceiver

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class DownloadService : JobIntentService() {
    companion object {
        val TAG: String = DownloadService::class.java.simpleName
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            enqueueWork(this, this::class.java, 101, intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "Download Service Started...")
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val notifyFinishedIntent = Intent(MainActivity.ACTION_DOWNLOAD_STATUS)
        sendBroadcast(notifyFinishedIntent)
    }
}