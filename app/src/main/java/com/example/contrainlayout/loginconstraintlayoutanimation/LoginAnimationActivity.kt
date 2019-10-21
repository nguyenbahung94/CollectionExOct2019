package com.example.contrainlayout.loginconstraintlayoutanimation

import android.os.Bundle
import android.os.PersistableBundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.contrainlayout.R
import kotlinx.android.synthetic.main.activity_login_animation.*

class LoginAnimationActivity : AppCompatActivity() {

    var isShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_animation)

        backgroundImage.setOnClickListener {
            if (isShow) {
                revertAnimation()
            } else {
                showAnimation()
            }
        }
    }

    private fun showAnimation() {
        isShow = true
        val conStraitSet = ConstraintSet()
        conStraitSet.clone(this, R.layout.activity_login_animation_2)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateInterpolator(1.0f)
        transition.duration = 800

        TransitionManager.beginDelayedTransition(cc1, transition)
        conStraitSet.applyTo(cc1)

    }

    private fun revertAnimation() {
        isShow = false
        val constrainSet = ConstraintSet()
        constrainSet.clone(this, R.layout.activity_login_animation)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateInterpolator(1.0f)
        transition.duration = 800

        TransitionManager.beginDelayedTransition(cc1, transition)
        constrainSet.applyTo(cc1)

    }


}