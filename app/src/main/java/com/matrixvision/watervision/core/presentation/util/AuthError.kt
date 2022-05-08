package com.matrixvision.watervision.core.presentation.util

import com.matrixvision.watervision.core.util.Error

sealed class AuthError: Error(){
    object FieldEmpty : AuthError()
    object InputTooShort: AuthError()
    object InvalidEmail: AuthError()
    object InvalidPassword: AuthError()
    object NotAcceptedTerms: AuthError()

}
