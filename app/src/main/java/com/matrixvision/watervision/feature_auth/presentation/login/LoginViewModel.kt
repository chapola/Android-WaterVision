package com.matrixvision.watervision.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matrixvision.watervision.R
import com.matrixvision.watervision.core.domain.state.StandardTextFieldState
import com.matrixvision.watervision.core.presentation.component.StandardTextField
import com.matrixvision.watervision.core.presentation.util.UiEvent
import com.matrixvision.watervision.core.util.Resource
import com.matrixvision.watervision.core.util.UiText
import com.matrixvision.watervision.feature_auth.domain.models.LoginResult
import com.matrixvision.watervision.feature_auth.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel(){

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _passwordState = mutableStateOf(StandardTextFieldState())
    val passwordState: State<StandardTextFieldState> = _passwordState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.EnteredEmail ->{
                _emailState.value = emailState.value.copy(
                    text = event.email
                )

            }
            is LoginEvent.EnteredPassword ->{
                _passwordState.value = passwordState.value.copy(
                    text = event.password
                )
            }
            is LoginEvent.TogglePasswordVisibility ->{
                _loginState.value = loginState.value.copy(
                    isPasswordVisible = !loginState.value.isPasswordVisible
                )
            }
            is LoginEvent.Login ->{
                login()

            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _emailState.value = emailState.value.copy(error = null)
            _passwordState.value = passwordState.value.copy(error = null)
            _loginState.value = loginState.value.copy(isLoading = true)
            val loginResult = loginUseCase(
                email = emailState.value.text,
                password = passwordState.value.text
            )

            _loginState.value = loginState.value.copy(isLoading = false)
            if (loginResult.emailError != null){
                _emailState.value = emailState.value.copy(
                    error = loginResult.emailError
                )
            }
            if (loginResult.passwordError != null){
                _passwordState.value = passwordState.value.copy(
                    error = loginResult.passwordError
                )
            }
            when(loginResult.result){
                is Resource.Success ->{
                    _eventFlow.send(
                        UiEvent.ShowSnackbar(UiText.StringResource(R.string.success_registration))
                    )

//                    _eventFlow.emit(UiEvent.OnLogin)
                }
                is Resource.Error ->{
                    _eventFlow.send(
                        UiEvent.ShowSnackbar(
                            loginResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }

    }


}