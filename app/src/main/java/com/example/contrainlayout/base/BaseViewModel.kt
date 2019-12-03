package com.example.contrainlayout.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.contrainlayout.extension.AppLog
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel<T>(application: Application) : AndroidViewModel(application) {

    var compositeDisposable = CompositeDisposable()
    var loading = MutableLiveData<Boolean>()
    var error = MutableLiveData<Throwable>()
    var result = MutableLiveData<T>()

    fun setErrorResult(throwable: Throwable) {
        loading.value = false
        error.value = throwable
        AppLog.logError(throwable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}