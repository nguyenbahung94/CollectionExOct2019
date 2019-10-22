package com.example.retrofitofflinecahing

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class CallApi {
    private lateinit var apiService: APIService
    fun setupRetrofitAndOkHttp(activity: Activity): APIService {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpCacheDirectory = File(activity.cacheDir, "offlineCache")
        //10MB
        val cacheSize = Cache(httpCacheDirectory, 10 * 1024 * 1024)

        val httpClient = OkHttpClient.Builder().run {
            cache(cacheSize)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(provideOfflineCacheInterceptor(activity))
            build()
        }

        val retrofit = Retrofit.Builder().run {
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create(Gson()))
            client(httpClient)
            baseUrl(APIService.BASE_URL)
                .build()
        }
        apiService = retrofit.create(APIService::class.java)
        return apiService
    }

/*
Document about no-cache,no-store v v//https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control?source=post_page-----71439ed32fda----------------------
* */
    private fun provideOfflineCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            request = if (hasNetWork(context)){
                /*
                      *  If there is Internet, get the cache that was stored 5 seconds ago.
                      *  If the cache is older than 5 seconds, then discard it,
                      *  and indicate an error in fetching the response.
                      *  The 'max-age' attribute is responsible for this behavior.
                      */
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            }else{
                /*
                       *  If there is no Internet, get the cache that was stored 7 days ago.
                       *  If the cache is older than 7 days, then discard it,
                       *  and indicate an error in fetching the response.
                       *  The 'max-stale' attribute is responsible for this behavior.
                       *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                       */
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            }
            return@Interceptor chain.proceed(request)
        }
    }
    private fun hasNetWork(context:Context):Boolean{
        var isConnected = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

}