package com.example.aslcodingtestproject.model.remote.interceptor

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber


// Http log
object MyHttpLoggingInterceptor {

    fun getInstance(): HttpLoggingInterceptor {
        /* Setup Interceptor to handle respond */
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->

            if (message.startsWith("{") || message.startsWith("[")) {
                try {
                    val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
                        .create()
                    val prettyPrintJson = gson.toJson(JsonParser.parseString(message))


                    Timber.i(
                        "===================This is the NetworkResponse json data=================="
                    )
                    val maxLogSize = 1000
                    for (i in 0..prettyPrintJson.length / maxLogSize) {
                        val start = i * maxLogSize
                        var end = (i + 1) * maxLogSize
                        end = if (end > prettyPrintJson.length) prettyPrintJson.length else end
                        Timber.i(prettyPrintJson.substring(start, end))
                    }

                } catch (m: JsonSyntaxException) {
                    Timber.e(message)
                }
            } else Timber.i(message)

        }.apply { level = HttpLoggingInterceptor.Level.BODY }

        return httpLoggingInterceptor
    }

}