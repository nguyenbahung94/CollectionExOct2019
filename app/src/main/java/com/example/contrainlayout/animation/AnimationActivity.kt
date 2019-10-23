package com.example.contrainlayout.animation

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.contrainlayout.R
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {
    private var selectedView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        setupAnimations()
    }

    private fun setupAnimations() {
        selectedView = null

        root.setOnClickListener {
            toDefault()
        }
        javaImg.setOnClickListener {
            if (selectedView != javaImg) {
                updateConstraints(R.layout.activity_animation_case_java)
                selectedView = javaImg
            } else
                toDefault()
        }

        kotlinImg.setOnClickListener {
            if (selectedView != kotlinImg) {
                updateConstraints(R.layout.activity_animation_case_kotlin)
                selectedView = kotlinImg
            } else
                toDefault()
        }

    }

    private fun toDefault() {
        if (selectedView != null) {
            updateConstraints(R.layout.activity_animation)
            selectedView = null
        }
    }

    private fun updateConstraints(@LayoutRes id: Int) {
        val newConstraintSet = ConstraintSet()
        newConstraintSet.clone(this, id)
        newConstraintSet.applyTo(root)
        val transition = ChangeBounds()
        transition.interpolator = OvershootInterpolator()
        TransitionManager.beginDelayedTransition(root, transition)
    }
}