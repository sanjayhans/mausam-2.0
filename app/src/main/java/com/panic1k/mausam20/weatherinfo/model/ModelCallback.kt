package com.panic1k.mausam20.weatherinfo.model

interface ModelCallback<T> {
    fun onSuccess(data: T)
    fun onError(throwable: Throwable)
}
