package com.example.aslcodingtestproject.model.remote.service

import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import retrofit2.Response
import retrofit2.http.*
import java.util.*

// service interface support retrofit & okhttp
interface PhotoService {
    @GET("photos")
    suspend fun getImg(): Response<List<GetPhotoRespItem>>

    @GET("photos/{id}/comments")
    suspend fun getImgDetail(
        @Path("id") id: String
    ): Response<List<GetPhotoDetailRespItem>>
}