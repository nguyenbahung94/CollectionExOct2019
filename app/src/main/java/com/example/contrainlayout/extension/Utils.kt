package com.example.contrainlayout.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.contrainlayout.R
import okhttp3.Interceptor
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

fun sendSMS(context: Context, phone: String?) {
    val smsIntent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null))
    if (smsIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(smsIntent)
    }
}

fun callPhone(context: Context, phone: String?) {
    val smsIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    if (smsIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(smsIntent)
    }
}

fun setLanguage(context: Context, language: String = "vi") {
    val res = context.resources
    val dm = res.displayMetrics
    val conf = res.configuration
    conf.setLocale(Locale(language)) // API 17+ only.
    res.updateConfiguration(conf, dm)
}

private val SOURCE_CHARACTERS = charArrayOf(
    'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò',
    'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã',
    'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú',
    'ý', 'ỷ', 'ỹ', 'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ',
    'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ',
    'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ',
    'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề',
    'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị',
    'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ',
    'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ',
    'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự'
)

private val DESTINATION_CHARACTERS = charArrayOf(
    'A', 'A', 'A', 'A', 'E', 'E', 'E', 'I', 'I',
    'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
    'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o',
    'o', 'u', 'u', 'y', 'y', 'y', 'A', 'a', 'D', 'd', 'I', 'i',
    'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a', 'A', 'a',
    'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a',
    'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a',
    'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e',
    'E', 'e', 'E', 'e', 'E', 'e', 'I', 'i', 'I', 'i', 'O',
    'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
    'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
    'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
    'U', 'u', 'U', 'u'
)

fun removeAccent(ch: Char): Char {
    var ch = ch
    val index = Arrays.binarySearch(SOURCE_CHARACTERS, ch)
    if (index >= 0) {
        ch = DESTINATION_CHARACTERS[index]
    }
    return ch
}

fun removeAccent(str: String?): String {
    str?.let {
        val sb = StringBuilder(str)
        for (i in sb.indices) {
            sb.setCharAt(i, removeAccent(sb[i]))
        }
        return sb.toString()
    }
    return ""
}

//custom header with token
/*
class ServiceInterceptor(var context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (request.header("No-Auth") == null) {
            val token = AppSettings.getInstance(context).getToken()
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else
            request.newBuilder().removeHeader("No-Auth").build()

        return chain.proceed(request)
    }

}*/

fun loadImage(
    context: Context,
    url: String?,
    imageView: ImageView, @DrawableRes defaultImage: Int = R.drawable.ic_launcher_background
) {
    if (url.isNullOrEmpty()) {
        Glide.with(context).load(defaultImage).into(imageView)
    } else {
        val urlImage =
            if (url.contains("https://") or url.contains("http://")) url else "https://$url"

        Glide.with(context)
            .load(urlImage)
            .error(defaultImage)
            .placeholder(defaultImage)
            .into(imageView)
    }
}

fun isFutureTime(date: String): Boolean {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    val now = Calendar.getInstance()
    val appStartTime = Calendar.getInstance()
    appStartTime.time = sdf.parse(date)
    return appStartTime.after(now)
}