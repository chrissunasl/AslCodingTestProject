package com.example.aslcodingtestproject.model.remote.responseobj

import java.io.Serializable

open class GetPhotoResp(
    var photo_id: Int?,
    var photo_url: String?,
) : BaseResponse()