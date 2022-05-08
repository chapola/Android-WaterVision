package com.matrixvision.watervision.feature_auth.data.repository

import android.content.SharedPreferences
import com.matrixvision.watervision.R
import com.matrixvision.watervision.core.data.dto.response.BasicApiResponse
import com.matrixvision.watervision.core.util.Resource
import com.matrixvision.watervision.core.util.SimpleResource
import com.matrixvision.watervision.core.util.UiText
import com.matrixvision.watervision.feature_auth.data.remote.AuthApi
import com.matrixvision.watervision.feature_auth.data.remote.CreateAccountRequest
import com.matrixvision.watervision.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val sharedPreferences: SharedPreferences
): AuthRepository {
    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        val request = CreateAccountRequest(email, username, password)
        return try {
           // val response = authApi.register(request = request)
            val response = BasicApiResponse(
                successful = true,
                message = "",
                data = null
            )
            delay(2000L)
            if (response.successful){
                Resource.Success(Unit)
            }else{
                response.message?.let {
                    Resource.Error(UiText.DynamicString(it))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }

        }catch (e: IOException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        }catch (e: HttpException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun login(email: String, password: String): SimpleResource {
        TODO("Not yet implemented")
    }

    override suspend fun authenticate(): SimpleResource {
        return try {
            //authApi.authenticate()
                delay(1000L)
            Resource.Success(Unit)
        }catch (e: IOException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        }catch (e: HttpException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

}