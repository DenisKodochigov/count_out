package com.count_out.framework.retrofit.upload

data class UploadResponse(
    val success: Boolean,
    val uploadedIds: List<Long>
)
