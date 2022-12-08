package com.example.aslcodingtestproject.model.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.aslcodingtestproject.MainApplication
import com.example.aslcodingtestproject.R
import com.example.aslcodingtestproject.model.remote.responseobj.BaseResponse
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import timber.log.Timber

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