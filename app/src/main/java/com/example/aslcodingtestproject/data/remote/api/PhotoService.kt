package com.example.aslcodingtestproject.data.remote.api

import com.example.aslcodingtestproject.data.remote.resp.PhotoCommentRespItem
import com.example.aslcodingtestproject.data.remote.resp.PhotoRespItem
import retrofit2.Response
import retrofit2.http.*
import java.util.*

// service interface support retrofit & okhttp(Let okhttp library describe with annotation)
interface PhotoService {
    // encapsulation of request para, path, etc
    @GET("photos")
    suspend fun getImg(): Response<List<PhotoRespItem>>

    @GET("photos/{id}/comments")
    suspend fun getImgComment(
        @Path("id") id: String
    ): Response<List<PhotoCommentRespItem>>
}