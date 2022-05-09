package com.matrixvision.watervision.feature_auth.domain.models

import com.matrixvision.watervision.core.presentation.util.AuthError
import com.matrixvision.watervision.core.util.SimpleResource

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)
