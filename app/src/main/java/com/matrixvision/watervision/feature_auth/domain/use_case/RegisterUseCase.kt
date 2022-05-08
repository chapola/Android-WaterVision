package com.matrixvision.watervision.feature_auth.domain.use_case

import com.matrixvision.watervision.core.domain.util.ValidationUtil
import com.matrixvision.watervision.feature_auth.domain.models.RegisterResult
import com.matrixvision.watervision.feature_auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
){
    suspend operator fun invoke(
        email: String,
        username: String,
        password: String,
        acceptedTerms: Boolean
    ): RegisterResult{
        val emailError = ValidationUtil.validateEmail(email = email)
        val usernameError = ValidationUtil.validateUsername(username)
        val passwordError = ValidationUtil.validatePassword(password)
        val acceptTermsError = ValidationUtil.validateAcceptTerms(acceptedTerms)

        if (emailError != null || usernameError !=null || passwordError != null || acceptTermsError != null){
            return RegisterResult(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
                acceptTermsError = acceptTermsError
            )
        }
        val result = repository.register(email.trim(), username.trim(), password.trim())
        return RegisterResult(
            result = result
        )
    }
}