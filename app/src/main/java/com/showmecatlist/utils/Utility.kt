package com.showmecatlist.utils

import android.content.Context
import android.net.ConnectivityManager


// TOP level kotlin file. This will hold all the top level kotlin utility functions
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}