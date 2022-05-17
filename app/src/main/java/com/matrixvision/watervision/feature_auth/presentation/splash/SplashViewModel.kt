package com.matrixvision.watervision.feature_auth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matrixvision.watervision.core.presentation.util.UiEvent
import com.matrixvision.watervision.core.util.Resource
import com.matrixvision.watervision.core.util.Screens
import com.matrixvision.watervision.feature_auth.domain.use_case.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
): ViewModel() {
    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        viewModelScope.launch {
            when(authenticationUseCase()){
                is Resource.Success ->{
                    // navigate to home screen
                    _eventFlow.send(
                        UiEvent.Navigate(Screens.LoginScreen.route)
                    )
                }
                is Resource.Error ->{
                    _eventFlow.send(
                        UiEvent.Navigate(Screens.LoginScreen.route)
                    )
                }
            }
        }
    }
}