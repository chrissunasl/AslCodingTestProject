package com.example.aslcodingtestproject.model.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request

// Header controller
object HeaderInterceptor {

    fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
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
            chain.proceed(request)
        }
    }


}