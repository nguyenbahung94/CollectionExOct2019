package com.example.contrainlayout.extension

import android.util.Log
import com.example.contrainlayout.BuildConfig


object AppLog {

    private val TAG = "Billy"

    fun log(message: String, tag: String = TAG) {
        if (BuildConfig.DEBUG)
            Log.i(tag, message)
    }

    fun logError(message: String, tag: String = TAG) {
        if (BuildConfig.DEBUG)
            Log.e(tag, message)
    }

    fun logError(throwable: Throwable, tag: String = TAG) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
            logError("Error Message: ${throwable.message}", tag)
        }

    }
}