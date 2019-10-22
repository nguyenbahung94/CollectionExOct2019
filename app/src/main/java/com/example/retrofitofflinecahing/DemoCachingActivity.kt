package com.example.retrofitofflinecahing

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contrainlayout.R
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_offline_caching.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.Exception
import java.util.concurrent.TimeUnit

class DemoCachingActivity : AppCompatActivity() {

    private lateinit var apiService: APIService
    private lateinit var callApi: CallApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_caching)
        callApi = CallApi()
        apiService = callApi.setupRetrofitAndOkHttp(this)
        btnRandomJoke.setOnClickListener {
            getRandomJokeFromAPI()
        }
    }

    @SuppressLint("CheckResult")
    private fun getRandomJokeFromAPI() {
        val observable = apiService.getRandomJoke("random")
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { map -> return@map map.value }
            .subscribe({ textView.text = it },
                {
                    Toast.makeText(
                        applicationContext,
                        "An error occurred in the Retrofit request. Perhaps no response/cache ${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
    }


}