package com.thinkcorp.navigation

import android.app.Application
import android.util.Log
import timber.log.Timber

/**
 * Created by william.quach on 27/05/2022.
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        Timber.d("onCreate")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Timber.d("onTrimMemory")
    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            FakeCrashLibrary.log(priority, tag, message)

            t?.let {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(it)
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(it)
                }
            }
        }

    }
}