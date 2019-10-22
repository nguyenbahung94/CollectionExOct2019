package com.example.contrainlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contrainlayout.extendedfloatingacctionbutton.ExtendedFloatingActivity
import com.example.contrainlayout.animation.CircularActivity
import com.example.contrainlayout.animation.startActivity
import com.example.contrainlayout.floatingwidget.FloatingWidgetActivity
import com.example.contrainlayout.loginconstraintlayoutanimation.LoginAnimationActivity
import com.example.retrofitofflinecahing.DemoCachingActivity
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
        btnopenfloatingwidget.setOnClickListener {
            startActivity<FloatingWidgetActivity>()
        }
        btnFloatActionButton.setOnClickListener {
            startActivity<ExtendedFloatingActivity>()
        }
        btnLoginAnimation.setOnClickListener {
            startActivity<LoginAnimationActivity>()
        }
        btnRetrofitCaching.setOnClickListener {
            startActivity<DemoCachingActivity>()
        }
    }
}
