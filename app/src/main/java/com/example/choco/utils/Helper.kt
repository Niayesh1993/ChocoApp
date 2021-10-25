package com.example.choco.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.choco.BuildConfig
import com.example.choco.ChocoApplication
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
            ChocoApplication.mContext!!.getContentResolver(),
            Settings.Secure.ANDROID_ID
        )
    }

    fun appVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    fun updateAppLanguage(context: Context) {
        val lang = "fa"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            updateResources(context, lang)
        }
        updateResourcesLegacy(context, lang)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun updateResources(context: Context, language: String) {
        val locale = getLocale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String) {
        val locale = getLocale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun getLocale(language: String): Locale {
        var locale: Locale? = null
        if (language.equals("zh-rCN", ignoreCase = true)) {
            locale = Locale.SIMPLIFIED_CHINESE
        } else if (language.equals("zh-rTW", ignoreCase = true)) {
            locale = Locale.TRADITIONAL_CHINESE
        }
        if (locale != null) {
            return locale
        }
        val split = language.split("-").toTypedArray()
        locale = if (split.size > 1) {
            Locale(split[0], split[1])
        } else {
            Locale(language)
        }
        return locale
    }

    fun await(milliseconds: Int) {
        val latch = CountDownLatch(1)
        try {
            latch.await(milliseconds.toLong(), TimeUnit.MILLISECONDS)
            latch.countDown()
        } catch (e: InterruptedException) {

        }
    }

    val isMainThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()

    fun assertMainThread() {
        if (!isMainThread) {
            throw AssertionError("Main-thread assertion failed.")
        }
    }

    fun postToMain(runnable: Runnable) {
        handler.post(runnable)
    }

    fun runOnMain(runnable: Runnable) {
        if (isMainThread) {
            runnable.run()
        } else {
            handler.post(runnable)
        }
    }

    fun runOnMainDelayed(runnable: Runnable, delayMillis: Long) {
        handler.postDelayed(runnable, delayMillis)
    }

    fun cancelRunnableOnMain(runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }

    fun runOnMainSync(runnable: Runnable) {
        if (isMainThread) {
            runnable.run()
        } else {
            val sync = CountDownLatch(1)
            runOnMain {
                try {
                    runnable.run()
                } finally {
                    sync.countDown()
                }
            }
            try {
                sync.await()
            } catch (ie: InterruptedException) {
                throw AssertionError(ie)
            }
        }
    }

    fun <K, T> deepCloneMap(original: Map<K, List<T?>>): HashMap<K, List<T?>> {
        val copy = HashMap<K, List<T?>>()
        if (!InputHelper.isEmpty(original)) {
            for ((key, value) in original) {
                if (InputHelper.isEmpty(value)) continue
                copy[key] = ArrayList(value)
            }
        }
        return copy
    }

    fun compareVersionNames(newVersionName: String): Int {
        return compareVersionNames(appVersion(), newVersionName)
    }

    fun compareVersionNames(oldVersionName: String, newVersionName: String): Int {
        var res = 0
        if (InputHelper.isEmpty(oldVersionName)
            || InputHelper.isEmpty(newVersionName)
        ) {
            return res
        }
        val oldNumbers = oldVersionName.split("\\.").toTypedArray()
        val newNumbers = newVersionName.split("\\.").toTypedArray()

        // To avoid IndexOutOfBounds
        val maxIndex = Math.min(oldNumbers.size, newNumbers.size)
        for (i in 0 until maxIndex) {
            val oldVersionPart = Integer.valueOf(oldNumbers[i])
            val newVersionPart = Integer.valueOf(newNumbers[i])
            if (oldVersionPart < newVersionPart) {
                res = -1
                break
            } else if (oldVersionPart > newVersionPart) {
                res = 1
                break
            }
        }

        // If versions are the same so far, but they have different length...
        if (res == 0 && oldNumbers.size != newNumbers.size) {
            res = if (oldNumbers.size > newNumbers.size) 1 else -1
        }
        return res
    }

    fun isLowRamDevice(context: Context): Boolean {
        var isLowRamDevice = true
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (activityManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            isLowRamDevice = activityManager.isLowRamDevice
        }
        return isLowRamDevice
    }

    fun isTelephonyEnabled(context: Context): Boolean {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm != null && tm.simState == TelephonyManager.SIM_STATE_READY
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
