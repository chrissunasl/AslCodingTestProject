package com.example.aslcodingtestproject.common.interceptor

import com.example.aslcodingtestproject.common.IConstants
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

// okhttp library client
object MyOkHttpClient {

    fun getOkHttpClient(): OkHttpClient {
        val timeoutInMilliSeconds: Long = IConstants.API.REQUEST_TIMEOUT

        val builder = OkHttpClient().newBuilder()
            .connectTimeout(timeoutInMilliSeconds, TimeUnit.MILLISECONDS) // connect timeout
            .readTimeout(timeoutInMilliSeconds, TimeUnit.MILLISECONDS) // socket timeout
            .writeTimeout(timeoutInMilliSeconds, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(HeaderInterceptor.getHeaderInterceptor())
            .addInterceptor(MyHttpLoggingInterceptor.getInstance())
            .cache(null)

        return builder.build()
    }

}