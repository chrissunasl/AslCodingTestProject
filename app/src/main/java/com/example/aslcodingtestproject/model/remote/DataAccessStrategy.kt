package com.example.aslcodingtestproject.model.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.aslcodingtestproject.MainApplication
import com.example.aslcodingtestproject.R
import com.example.aslcodingtestproject.model.remote.responseobj.BaseResponse
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import timber.log.Timber

// Directly return livedata + Resource
fun <A> performNonTokenGetOperation(
    networkCall: suspend () -> Response<A>,
    getCallResult: suspend (A?) -> Unit = {},
): LiveData<Resource<A>> = liveData(Dispatchers.IO) {

    Timber.d("performBaseGetOperation().start() **********************")
    emit(Resource.loading())


    try {
        val response = networkCall.invoke()

        // Decrypt Data and update database
        getCallResult.invoke(response.body())
        emit(Resource.success(response.body()))


    } catch (e: Exception) {
        Timber.e("performBaseGetOperation(), \n$e")
        emit(Resource.error("Error", null))
        // Update database
        getCallResult.invoke(null)
    }
}

// Directly return Resource
suspend fun <A> performNonTokenNormalGetOperation(
    networkCall: suspend () -> Response<A>,
    getCallResult: suspend (A?) -> Unit = {},
): Resource<A> {

    Log.d("chris","performBaseGetOperation().start() **********************")
    Resource.loading(null)

    return try {
        val response = networkCall.invoke()

        // Decrypt Data and update database
        Log.d("chris"," ${response.body()}")
        getCallResult.invoke(response.body())
        Resource.success(response.body())


    } catch (e: Exception) {
        Timber.e("performBaseGetOperation(), \n$e")
        getCallResult.invoke(null)
        Resource.error("Error", null)
        // Update database

    }
}

