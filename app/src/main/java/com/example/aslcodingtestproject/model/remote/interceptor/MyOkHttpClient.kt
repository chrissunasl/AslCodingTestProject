package com.example.aslcodingtestproject.model.remote.interceptor

import android.content.Context
import com.autotoll.ffts.model.constant.IConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

// okhttp library client
object MyOkHttpClient {

    fun getOkHttpClient(
        @ApplicationContext appContext: Context,
        authenticator: Authenticator? = null,
        isNeedApiAccessToken: Boolean = true,
    ): OkHttpClient {
        val timeoutInSeconds: Long = IConstants.BASIC.BASIC_REQUEST_TIMEOUT

        val builder = OkHttpClient().newBuilder()
            .connectTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS) // connect timeout
            .readTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS) // socket timeout
            .writeTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(HeaderInterceptor.getHeaderInterceptor(appContext, isNeedApiAccessToken))
            .addInterceptor(MyHttpLoggingInterceptor.getInstance())
            .cache(null)

        // Add authenticator for normal API, but getAccessToken() is not one of them
        if (authenticator != null)
            builder.authenticator(authenticator)

        return builder.build()
    }

}