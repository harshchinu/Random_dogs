package com.example.doggo.data.model

data class ResponseWrapper<T>(
    val data: T? = null,
    val error: String? = null,
    val type: ResponseDataType
) {
    fun isSuccess() = type == ResponseDataType.SUCCESS

    companion object {
        fun <R> success(data: R): ResponseWrapper<R> {
            return ResponseWrapper(data = data, type = ResponseDataType.SUCCESS)
        }

        fun <R> error(error: String? = null): ResponseWrapper<R> {
            return ResponseWrapper(error = error, type = ResponseDataType.ERROR)
        }
    }

    enum class ResponseDataType {
        SUCCESS,
        ERROR
    }
}
