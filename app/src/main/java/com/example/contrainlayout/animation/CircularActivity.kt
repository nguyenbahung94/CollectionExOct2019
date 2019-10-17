package com.example.contrainlayout.animation

import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.contrainlayout.R
import kotlinx.android.synthetic.main.activity_circular.*
import java.util.concurrent.TimeUnit

class CircularActivity : AppCompatActivity() {

    lateinit var clockAnimator: ValueAnimator

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular)
        clockAnimator = animatePointer(TimeUnit.SECONDS.toMillis(10))
        imgClock.setOnClickListener {
            when {
                clockAnimator.isPaused -> {
                    clockAnimator.resume()
                    Toast.makeText(applicationContext, "Resumed", Toast.LENGTH_SHORT).show()
                }
                clockAnimator.isRunning -> {
                    Toast.makeText(applicationContext, "Paused", Toast.LENGTH_SHORT).show()
                    clockAnimator.pause()
                }
                else -> clockAnimator.start()
            }
        }
    }

    private fun animatePointer(orbitDuration: Long): ValueAnimator {
        val anim = ValueAnimator.ofInt(0, 359)
        anim.addUpdateListener { valueAnimator ->
            val temp = valueAnimator!!.animatedValue as Int
            val layoutParams = imgPointer.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.circleAngle = temp.toFloat()
            Log.e("circleAngle = ", temp.toString())
            imgPointer.layoutParams = layoutParams
        }
        anim.duration = orbitDuration
        anim.interpolator = LinearInterpolator()
        anim.repeatMode = ValueAnimator.RESTART
        anim.repeatCount = ValueAnimator.INFINITE
        return anim
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onResume() {
        super.onResume()
        if (clockAnimator.isPaused) {
            clockAnimator.resume()
        }

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onPause() {
        super.onPause()
        if (clockAnimator.isRunning) {
            clockAnimator.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (clockAnimator.isRunning) {
            clockAnimator.cancel()
        }
    }
}