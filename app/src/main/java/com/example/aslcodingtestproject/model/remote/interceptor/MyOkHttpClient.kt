package com.example.aslcodingtestproject.model.remote.interceptor

import android.content.Context
import com.autotoll.ffts.model.constant.IConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

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

//        if (!IConstants.isProduction) {
//        builder = UnSaveHttpClientManager().makeItUnSafe(builder)
//        }

        // Add authenticator for normal API, but getAccessToken() is not one of them
        if (authenticator != null)
            builder.authenticator(authenticator)

        return builder.build()
    }

    fun getHttpClientAfterLogin(
        @ApplicationContext appContext: Context,
        authenticator: Authenticator? = null,
    ): OkHttpClient {
        val timeoutInSeconds: Long = IConstants.BASIC.BASIC_REQUEST_TIMEOUT

        val builder = OkHttpClient().newBuilder()
            .connectTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS) // connect timeout
            .readTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS) // socket timeout
            .writeTimeout(timeoutInSeconds, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(HeaderInterceptor.getSessionHeaderInterceptor(appContext))
            .addInterceptor(MyHttpLoggingInterceptor.getInstance())
            .cache(Cache(appContext.cacheDir, 10L * 1024L * 1024L))

//        if (!IConstants.isProduction) {
//        builder = UnSaveHttpClientManager().makeItUnSafe(builder)
//        }

        // Add authenticator for normal API, but getAccessToken() is not one of them
        if (authenticator != null)
            builder.authenticator(authenticator)

        return builder.build()
    }

}