package com.example.aslcodingtestproject.data

import com.example.aslcodingtestproject.common.Resource
import retrofit2.Response
import timber.log.Timber

// Directly return Resource
suspend fun <A> performPhotoOperation(
    networkCall: suspend () -> Response<A>,
    callResult: suspend (A?) -> Unit = {},
): Resource<A>{
    return try {
        val response = networkCall.invoke()
        Timber.tag("DAS").d(" %s", response.body())
        callResult.invoke(response.body())
        Resource.success(response.body())
    } catch (e: Exception) {
        Timber.e("performBaseGetOperation(), \n$e")
        callResult.invoke(null)
        Resource.error("Error", null)
    }
}


