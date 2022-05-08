package com.matrixvision.watervision.core.domain.state

import com.matrixvision.watervision.core.util.Error

data class CheckBoxState(
    val isChecked: Boolean = false,
    val error: Error? =null
)
