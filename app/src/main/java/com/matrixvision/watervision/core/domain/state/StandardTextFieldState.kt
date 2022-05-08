package com.matrixvision.watervision.core.domain.state

import com.matrixvision.watervision.core.util.Error

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)
