package com.example.contrainlayout.extendedfloatingacctionbutton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contrainlayout.R
import kotlinx.android.synthetic.main.activity_extended_floating_action.*

class ExtendedFloatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extended_floating_action)
        extFab.setOnClickListener {
            if (extFab.isExpanded) {
                extFab.hide()
            } else {
                extFab.show()
            }
        }
        extFab2.setOnClickListener {
            if (extFab.isShown) {
                extFab.hide()
            } else {
                extFab.show()
            }
        }
    }
}