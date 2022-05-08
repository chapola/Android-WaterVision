package com.matrixvision.watervision.core.data.dto.response

import com.matrixvision.watervision.core.util.Resource

data class BasicApiResponse<T>(
    val successful: Boolean,
    val message: String? = null,
    val data: T? = null
)
