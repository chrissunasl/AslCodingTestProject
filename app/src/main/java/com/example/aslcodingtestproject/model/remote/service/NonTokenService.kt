package com.example.aslcodingtestproject.model.remote.service

import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import retrofit2.Response
import retrofit2.http.*
import java.util.*

// service interface support retrofit & okhttp
interface NonTokenService {
    @GET("photos")
    suspend fun getImg(
        @Header("timestamp") timestamp: String,
    ): Response<ArrayList<GetPhotoRespItem>>

    @GET("photos/{id}/comments")
    suspend fun getImgDetail(
        @Header("timestamp") timestamp: String,
        @Path("id") id: String
    ): Response<ArrayList<GetPhotoDetailRespItem>>
}