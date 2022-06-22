package com.unlam.feat.common

import android.util.Log
import com.google.gson.Gson

fun <T, Y>print(request: T, response: Y) {
    val TAG = "FeatDebugger"
    val gson = Gson()
    Log.i(TAG, "::::::::::::::::::: INIT LOG :::::::::::::::::::")
    Log.i(TAG, "Response => ${gson.toJson(response)}")
    Log.i(TAG, "Request => ${request.toString()}")
    Log.i(TAG, "::::::::::::::::::: END LOG :::::::::::::::::::")
}
