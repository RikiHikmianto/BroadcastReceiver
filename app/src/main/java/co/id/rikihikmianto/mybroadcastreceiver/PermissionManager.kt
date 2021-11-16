package co.id.rikihikmianto.mybroadcastreceiver

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionManager {
    fun check(activity: Activity, permission: String, request: Int) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(activity, arrayOf(permission), request)
    }
}