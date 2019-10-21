package com.example.contrainlayout.floatingwidget

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.provider.Settings.canDrawOverlays
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_floating_widget.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class FloatingWidgetActivity : AppCompatActivity() {
    companion object {
        const val DRAW_OVER_OTHER_APP_PERMISSION = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.contrainlayout.R.layout.activity_main_floating_widget)
        askForSystemOverlayPermission()
        val badge_count = intent.getIntExtra("badge_count", 0)
        textView.text = "$badge_count messages received previously"

        button.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || canDrawOverlays(this@FloatingWidgetActivity)) {
                startService(Intent(this@FloatingWidgetActivity, FloatingWidgetService::class.java))
            }else{
                errorToast()
            }
        }

    }

    private fun askForSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !canDrawOverlays(this)) {

            //If the draw over permission is not available to open the settings screen
            //to grant the permission.
            val intent = Intent(
                ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION)
        }
    }

    override fun onPause() {
        super.onPause()
        // To prevent starting the service if the required permission is NOT granted.
        // To prevent starting the service if the required permission is NOT granted.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || canDrawOverlays(this)) {
            startService(
                Intent(this@FloatingWidgetActivity, FloatingWidgetService::class.java).putExtra(
                    "activity_background",
                    true
                )
            )
         //   finish()
        } else {
            errorToast()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                errorToast()
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun errorToast() {
        Toast.makeText(
            this,
            "Draw over other app permission not available. Can't start the application without the permission.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDestroy() {
        stopService(Intent(this, FloatingWidgetActivity::class.java))
        super.onDestroy()
    }
}