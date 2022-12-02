package com.example.aslcodingtestproject.model.remote.responseobj

import java.io.Serializable

open class BaseResponse(
    var result: Boolean? = false,
    var result_code: Int? = -1,
    var message: String? = "",
) : Serializable