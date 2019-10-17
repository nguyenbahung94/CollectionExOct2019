package com.example.contrainlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contrainlayout.animation.CircularActivity
import com.example.contrainlayout.animation.startActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        eventClick()
    }

    private fun eventClick() {
        btnCircular.setOnClickListener {
         startActivity<CircularActivity>()
        }
    }
}
