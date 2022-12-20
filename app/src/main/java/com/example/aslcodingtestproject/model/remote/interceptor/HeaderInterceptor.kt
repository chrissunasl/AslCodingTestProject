package com.example.aslcodingtestproject.model.remote.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketException

// Header controller
object HeaderInterceptor {


    fun getHeaderInterceptor(
        appContext: Context,
        isNeedAccessToken: Boolean,
    ): Interceptor {
        return object : Interceptor {

            @Throws(Exception::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

                val original: Request = chain.request()

                // Request Builder
                val builder = original.newBuilder()

                builder.header("Content-Type", "application/json")
                builder.headers(original.headers)

                builder.header("lang", "en")

                // Setup method, body
                builder.method(original.method, original.body)
                    .build()

                val request = builder.build()
                return chain.proceed(request)



            }

        }
    }


    fun getSessionHeaderInterceptor(appContext: Context): Interceptor {
        return object : Interceptor {

            @Throws(Exception::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val original: Request = chain.request()

                // Request Builder
                val builder = original.newBuilder()
                builder.header("Content-Type", "application/json")
                // Add Headers from Service interface
                builder.headers(original.headers)
                // Add Language
                builder.header("lang", "en")

                // Add Session Access Token
                //builder.header("Authorization", "Bearer $accessToken")
//                builder.addHeader("Connection", "keep-alive")
                // Setup method, body
                builder.method(original.method, original.body)
                    .build()

                val request = builder.build()
                return chain.proceed(request)

            }
        }
    }
}