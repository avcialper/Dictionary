package com.avcialper.dictionary.util.constants

sealed class Resource<T>(
    val data: T?,
    val throwable: Throwable?,
    val status: ResourceStatus,
    val errorMessage: String?
) {

    class Success<T>(data: T?) :
        Resource<T>(
            data, null,
            ResourceStatus.SUCCESS, null
        )

    class Loading<T> :
        Resource<T>(
            null, null,
            ResourceStatus.LOADING, null
        )

    class Error<T>(data: T?, throwable: Throwable?, errorMessage: String?) :
        Resource<T>(
            data, throwable,
            ResourceStatus.ERROR, errorMessage
        )
}