package com.example.aslcodingtestproject.model.remote.service


import com.example.aslcodingtestproject.model.database.structure.PhotoBase
import com.example.aslcodingtestproject.model.remote.requestobj.BaseRequest
import com.example.aslcodingtestproject.model.remote.responseobj.BaseResponse
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoResp
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface NonTokenService {

    //sample
    @POST("photos")
    suspend fun getPhoto(
        @Header("signature") signature: String,
        @Header("timestamp") timestamp: String,
        @Body request: BaseRequest,
    ): Response<BaseResponse>

    @GET("photos")
    suspend fun getImg(
        @Query("timestamp") timestamp: String,
    ): Response<GetPhotoResp>

}