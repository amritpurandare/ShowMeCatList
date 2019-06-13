package com.showmecatlist.utils

import android.content.Context
import android.net.ConnectivityManager


// TOP level kotlin file. This will hold all the top level kotlin utility functions

// This function returns true if network is connected else false
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}