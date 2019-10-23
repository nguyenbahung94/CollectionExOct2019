package com.example.contrainlayout.animation

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.contrainlayout.R
import kotlinx.android.synthetic.main.activity_animation_main_shopping.*

class AnimationShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_main_shopping)
        setupAnimations()
    }

    private fun setupAnimations() {
        askSize.setOnClickListener {
            updateConstraints(R.layout.activity_animation_shopping)
            askSize.text = "ADD TO CART -1234 INR"
        }
        close.setOnClickListener {
            updateConstraints(R.layout.activity_animation_main_shopping)
            askSize.text = "SELECT SIZE"
        }
    }

    private fun updateConstraints(@LayoutRes id: Int) {
        val newConstraintSet = ConstraintSet()
        newConstraintSet.clone(this, id)
        newConstraintSet.applyTo(shoppingRoot)
        val transition = ChangeBounds()
        transition.interpolator = OvershootInterpolator()
        TransitionManager.beginDelayedTransition(shoppingRoot, transition)
    }
}