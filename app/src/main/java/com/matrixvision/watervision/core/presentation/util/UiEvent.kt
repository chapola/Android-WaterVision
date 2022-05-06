package com.matrixvision.watervision.core.presentation.util

import com.matrixvision.watervision.core.util.Event
import com.matrixvision.watervision.core.util.UiText

sealed class UiEvent: Event(){
    data class ShowSnackbar(val uiText: UiText): UiEvent()
    data class Navigate(val route: String): UiEvent()
    object NavigateUp: UiEvent()
    object OnLogin: UiEvent()
}
