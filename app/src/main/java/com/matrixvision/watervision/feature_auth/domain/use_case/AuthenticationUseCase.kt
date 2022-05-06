package com.matrixvision.watervision.feature_auth.domain.use_case

import com.matrixvision.watervision.core.util.SimpleResource
import com.matrixvision.watervision.feature_auth.domain.repository.AuthRepository

class AuthenticationUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): SimpleResource{
        return repository.authenticate()
    }
}