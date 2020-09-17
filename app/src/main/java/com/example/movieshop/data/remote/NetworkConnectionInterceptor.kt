package com.example.movieshop.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import com.example.movieshop.BuildConfig.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val appContext = context.applicationContext


    override fun intercept(chain: Interceptor.Chain): Response {
        if (!idInternetAvailable()) throw NoInternetException(
            "Verify your data connection"
        )
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(
            request
        )
    }

    private fun idInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(TRANSPORT_WIFI) -> true
                    hasTransport(TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }
}