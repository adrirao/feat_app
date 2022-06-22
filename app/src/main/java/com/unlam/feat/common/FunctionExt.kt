package com.unlam.feat.common

import android.util.Log
import com.google.gson.Gson

fun <T, Y>print(request: T, response: Y) {
    val TAG = "FeatDebugger"
    val gson = Gson()
    Log.e(TAG, "::::::::::::::::::: INIT LOG :::::::::::::::::::")
    Log.e(TAG, "Response => ${gson.toJson(response)}")
    Log.e(TAG, "Request => ${request.toString()}")
    Log.e(TAG, "::::::::::::::::::: END LOG :::::::::::::::::::")
}
