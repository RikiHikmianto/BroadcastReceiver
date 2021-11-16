package co.id.rikihikmianto.mybroadcastreceiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import co.id.rikihikmianto.mybroadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val SMS_REQUEST_CODE = 101
        const val ACTION_DOWNLOAD_STATUS = "download_status"
    }

    private lateinit var downloadReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPermission.setOnClickListener(this)
        binding.btnDownload.setOnClickListener(this)

        downloadReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d(DownloadService.TAG,"Download Completed")
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnPermission -> PermissionManager.check(
                this,
                Manifest.permission.RECEIVE_SMS,
                SMS_REQUEST_CODE
            )
            R.id.btnDownload -> {
                val downloadIntetService =  Intent(this, DownloadService::class.java)
                startService(downloadIntetService)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> Toast.makeText(
                    this,
                    "SMS receiver permission accepted",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Toast.makeText(this, "SMS receiver permission rejected", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}