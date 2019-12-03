package com.example.contrainlayout.base

class BaseResponse<T> {
    val code: String? = null
    val message: String? = null
    val value: T? = null
    val isSuccess: Boolean = false
}