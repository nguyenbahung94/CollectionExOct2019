package com.example.contrainlayout.animation

import android.app.Activity
import android.content.Context
import android.content.Intent

/* Simple Using
startActivity<MainActivity>()

With extra
startActivity<MainActivity>{
   putExtra("param 1", "Simple")
}
* */
inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit={}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}