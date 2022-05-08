package com.matrixvision.watervision.feature_auth.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matrixvision.watervision.R
import com.matrixvision.watervision.core.domain.state.CheckBoxState
import com.matrixvision.watervision.core.domain.state.PasswordTextFieldState
import com.matrixvision.watervision.core.domain.state.StandardTextFieldState
import com.matrixvision.watervision.core.presentation.util.UiEvent
import com.matrixvision.watervision.core.util.Resource
import com.matrixvision.watervision.core.util.UiText
import com.matrixvision.watervision.feature_auth.domain.use_case.RegisterUseCase
import dagger.hilt.android.internal.modules.HiltWrapper_ActivityModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel(){
    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _acceptTerms = mutableStateOf(CheckBoxState())
    val acceptTerms: State<CheckBoxState> = _acceptTerms

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _onRegister = MutableSharedFlow<Unit>(replay = 1)
    val onRegister = _onRegister.asSharedFlow()

    fun onEvent(event: RegisterEvent){
        when(event){
            is RegisterEvent.EnteredUsername ->{
                _usernameState.value = _usernameState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredEmail ->{
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredPassword ->{
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.TogglePasswordVisibility ->{
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
            is RegisterEvent.AcceptTermsAndCondition ->{
                _acceptTerms.value = _acceptTerms.value.copy(
                    isChecked = event.isAccepted
                )

            }
            is RegisterEvent.Register ->{
                register()
            }
        }

    }

    private fun register() {
        viewModelScope.launch {
            _emailState.value = emailState.value.copy(error = null)
            _usernameState.value = usernameState.value.copy(error = null)
            _passwordState.value = passwordState.value.copy(error = null)
            _acceptTerms.value = acceptTerms.value.copy(error = null)
            _registerState.value = RegisterState(isLoading = true)

            val registerResult = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text,
                acceptedTerms = acceptTerms.value.isChecked

            )
            if (registerResult.emailError != null) {
                _emailState.value = emailState.value.copy(
                    error = registerResult.emailError
                )
            }
            if (registerResult.usernameError != null){
                _usernameState.value = usernameState.value.copy(
                    error = registerResult.usernameError
                )
            }
            if (registerResult.passwordError != null){
                _passwordState.value = passwordState.value.copy(
                    error = registerResult.passwordError
                )
            }
            if (registerResult.acceptTermsError != null){
                _acceptTerms.value = acceptTerms.value.copy(
                    error = registerResult.acceptTermsError
                )
            }
            when(registerResult.result){
                is Resource.Success ->{
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(UiText.StringResource(R.string.success_registration))
                    )
                    _onRegister.emit(Unit)
                    _registerState.value = RegisterState(isLoading = false)
//                    _usernameState.value = StandardTextFieldState()
//                    _emailState.value = StandardTextFieldState()
//                    _passwordState.value = PasswordTextFieldState()
//                    _acceptTerms.value = CheckBoxState(isChecked = false)

                }
                is Resource.Error ->{
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(registerResult.result.uiText ?: UiText.unknownError())
                    )
                    _registerState.value = RegisterState(isLoading = false)
                }
                null ->{
                    _registerState.value = RegisterState(isLoading = false)
                }
            }
        }
    }



}
