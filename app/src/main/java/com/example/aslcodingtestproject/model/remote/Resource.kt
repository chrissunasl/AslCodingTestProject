package com.example.aslcodingtestproject.model.remote

import com.example.aslcodingtestproject.model.remote.responseobj.BaseResponse


data class Resource<out T>(val status: Status, val data: T?, val message: String?, val response: BaseResponse? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> success(): Resource<T> {
            return Resource(Status.SUCCESS, null, null)
        }

        fun <T> error(message: String): Resource<T> {
            return Resource(Status.ERROR, null, message, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message, null)
        }

        fun <T> error(message: String, data: T? = null, response: BaseResponse): Resource<T> {
            return Resource(Status.ERROR, data, message, response)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}