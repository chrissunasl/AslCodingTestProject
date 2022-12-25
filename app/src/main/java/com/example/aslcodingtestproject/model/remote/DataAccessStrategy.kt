package com.example.aslcodingtestproject.model.remote

import retrofit2.Response
import timber.log.Timber

// Directly return Resource
suspend fun <A> performNonTokenNormalGetOperation(
    networkCall: suspend () -> Response<A>,
    getCallResult: suspend (A?) -> Unit = {},
): Resource<A> {

    Timber.tag("DAS").d("performBaseGetOperation().start() **********************")
    Resource.loading(null)

    return try {
        val response = networkCall.invoke()
        // Decrypt Data and update database
        Timber.tag("DAS").d(" %s", response.body())
        getCallResult.invoke(response.body())
        Resource.success(response.body())
    } catch (e: Exception) {
        Timber.e("performBaseGetOperation(), \n$e")
        getCallResult.invoke(null)
        Resource.error("Error", null)
        // Update database

    }
}

