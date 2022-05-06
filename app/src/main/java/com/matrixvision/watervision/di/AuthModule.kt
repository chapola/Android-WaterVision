package com.matrixvision.watervision.di

import android.content.SharedPreferences
import com.matrixvision.watervision.feature_auth.data.remote.AuthApi
import com.matrixvision.watervision.feature_auth.data.repository.AuthRepositoryImpl
import com.matrixvision.watervision.feature_auth.domain.repository.AuthRepository
import com.matrixvision.watervision.feature_auth.domain.use_case.AuthenticationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

//    @Provides
//    @Singleton
//    fun provideAuthApi(client: OkHttpClient): AuthApi{
//
//    }

    @Provides
    @Singleton
    fun provideAuthRepository(
       // api: AuthApi,
        sharedPreferences: SharedPreferences
    ): AuthRepository{
        return AuthRepositoryImpl(
            //api,
            sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthenticationUseCase(repository: AuthRepository): AuthenticationUseCase{
        return AuthenticationUseCase(repository = repository)
    }
}