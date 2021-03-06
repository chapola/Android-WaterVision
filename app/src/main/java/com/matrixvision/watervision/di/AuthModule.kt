package com.matrixvision.watervision.di

import android.content.SharedPreferences
import com.matrixvision.watervision.feature_auth.data.remote.AuthApi
import com.matrixvision.watervision.feature_auth.data.repository.AuthRepositoryImpl
import com.matrixvision.watervision.feature_auth.domain.repository.AuthRepository
import com.matrixvision.watervision.feature_auth.domain.use_case.AuthenticationUseCase
import com.matrixvision.watervision.feature_auth.domain.use_case.LoginUseCase
import com.matrixvision.watervision.feature_auth.domain.use_case.RegisterUseCase
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

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthApi{
        return Retrofit.Builder()
            .baseUrl("https://google.com")
            .client(client)
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
       api: AuthApi,
        sharedPreferences: SharedPreferences
    ): AuthRepository{
        return AuthRepositoryImpl(
            api,
            sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthenticationUseCase(repository: AuthRepository): AuthenticationUseCase{
        return AuthenticationUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase{
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase{
        return LoginUseCase(repository = repository)
    }
}