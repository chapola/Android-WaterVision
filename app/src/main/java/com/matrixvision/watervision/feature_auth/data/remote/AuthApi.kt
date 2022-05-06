package com.matrixvision.watervision.feature_auth.data.remote

import retrofit2.http.GET

interface AuthApi {


    @GET("authenticate")
    suspend fun authenticate()
}