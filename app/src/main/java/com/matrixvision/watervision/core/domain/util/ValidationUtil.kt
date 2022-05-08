package com.matrixvision.watervision.core.domain.util

import android.util.Patterns
import com.matrixvision.watervision.core.presentation.util.AuthError
import com.matrixvision.watervision.core.util.Constants

object ValidationUtil {
    fun validateEmail(email: String): AuthError?{
        val trimmedEmail = email.trim()

        if (trimmedEmail.isBlank()){
            return AuthError.FieldEmpty
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return AuthError.InvalidEmail
        }
        return null

    }
    fun validateUsername(username: String): AuthError?{
        val trimmedUsername = username.trim()
        if (trimmedUsername.isBlank()){
            return AuthError.FieldEmpty
        }
        if (trimmedUsername.length< Constants.MIN_USERNAME_LENGTH){
            return AuthError.InputTooShort
        }
        return null
    }

    fun validatePassword(password: String): AuthError?{
        if (password.isBlank()){
            return AuthError.FieldEmpty
        }
        if (password.length < Constants.MIN_PASSWORD_LENGTH){
            return AuthError.InputTooShort
        }
        val capitalLetterInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }

        if (!capitalLetterInPassword || !numberInPassword){
            return AuthError.InvalidPassword
        }
        return null
    }

    fun validateAcceptTerms(isAccepted: Boolean):AuthError?{
        if (!isAccepted){
            return AuthError.NotAcceptedTerms
        }
        return null
    }
}