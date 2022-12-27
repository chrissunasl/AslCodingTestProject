package com.example.aslcodingtestproject.model.remote.interceptor

import com.example.aslcodingtestproject.constant.IConstants
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

// okhttp library client
object MyOkHttpClient {

    fun getOkHttpClient(): OkHttpClient {
        val timeoutInSeconds: Long = IConstants.BASIC.BASIC_REQUEST_TIMEOUT

        val builder = OkHttpClient().newBuilder()
            .connectTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS) // connect timeout
            .readTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS) // socket timeout
            .writeTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(HeaderInterceptor.getHeaderInterceptor())
            .addInterceptor(MyHttpLoggingInterceptor.getInstance())
            .cache(null)

        return builder.build()
    }

}