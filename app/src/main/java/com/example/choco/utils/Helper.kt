package com.example.choco.utils

import android.app.ActivityManager
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.choco.BuildConfig
import com.example.choco.di.MediaApplication
import java.lang.AssertionError
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object Helper {
    var handler = Handler(Looper.getMainLooper())
    val isDebug: Boolean
        get() = !isRelease
    val isRelease: Boolean
        get() = BuildConfig.ENABLE_RELEASE_FEATURES

    @SuppressLint("HardwareIds")
    fun deviceKey(): String {
        return Settings.Secure.getString(
            MediaApplication.mContext!!.getContentResolver(),
            Settings.Secure.ANDROID_ID
        )
    }

    fun appVersion(): String {
        return BuildConfig.VERSION_NAME
    }


    val isMainThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()



    fun runOnMain(runnable: Runnable) {
        if (isMainThread) {
            runnable.run()
        } else {
            handler.post(runnable)
        }
    }



    fun areNotificationsEnabled(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (!manager.areNotificationsEnabled()) {
                return false
            }
            val channels = manager.notificationChannels
            for (channel in channels) {
                if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                    return false
                }
            }
            true
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
    }

    fun isAtLeast(versionCode: Int): Boolean {
        return Build.VERSION.SDK_INT >= versionCode
    }

}
