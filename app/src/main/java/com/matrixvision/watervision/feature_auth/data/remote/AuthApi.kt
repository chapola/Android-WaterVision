package com.matrixvision.watervision.feature_auth.data.remote

import com.matrixvision.watervision.core.data.dto.response.BasicApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ):BasicApiResponse<Unit>


    @GET("authenticate")
    suspend fun authenticate()
}