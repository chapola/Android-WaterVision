package com.matrixvision.watervision.feature_auth.domain.models

import com.matrixvision.watervision.core.presentation.util.AuthError
import com.matrixvision.watervision.core.util.SimpleResource

data class RegisterResult(
    val emailError: AuthError? = null,
    val usernameError: AuthError? =null,
    val passwordError: AuthError? =null,
    val acceptTermsError: AuthError? = null,
    val result: SimpleResource? = null
)
