package com.example.contrainlayout.extension

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setSource(url: String, options: RequestOptions? = null) {
    var r = Glide.with(this)
        .load(url)
    options?.let {
        r = r.apply(it)
    }
    r.into(this)
}

fun View.preventMultiClick() {
    isEnabled = false
    postDelayed({
        isEnabled = true
    }, 500)
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}
fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}