package com.example.retrofitofflinecahing

import com.google.gson.annotations.SerializedName


class Jokes {
    @SerializedName("url")
    var url: String? = null
    @SerializedName("icon_url")
    var icon_url: String? = null
    @SerializedName("value")
    var value: String? = null
}