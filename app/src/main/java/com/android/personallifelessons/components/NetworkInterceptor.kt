package com.android.personallifelessons.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(
    private val context: Context
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isInternetAvailable())
            throw Exception("No Internet available")
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.let{
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.let{
                when{
                    it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            } ?: false
        }
    }
}