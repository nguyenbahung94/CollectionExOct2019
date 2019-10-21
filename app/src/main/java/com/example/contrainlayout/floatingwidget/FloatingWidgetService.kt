package com.example.contrainlayout.floatingwidget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.IBinder
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.RelativeLayout
import com.andremion.counterfab.CounterFab
import com.example.contrainlayout.R
import kotlin.math.abs
import kotlin.math.roundToInt


class FloatingWidgetService : Service() {
    lateinit var mWindowManager: WindowManager
    var mOverLayView: View? = null
    private lateinit var counterFab: CounterFab
    private var mWidth = 0
    var activity_background = false


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            activity_background = intent.getBooleanExtra("activity_background", false)
        }
        if (mOverLayView == null) {
            mOverLayView =
                LayoutInflater.from(this)
                    .inflate(R.layout.overlay_layout_floating_widget, null)
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                // WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,// for android above 26(android 8)
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT
            )

            //Specify the view position
            //Initially view will be added to top-left corner
            params.gravity = Gravity.TOP.and(Gravity.START)
            params.x = 0
            params.y = 100

            mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            mWindowManager.addView(mOverLayView, params)

            val display = mWindowManager.defaultDisplay
            val size = Point()
            display.getSize(size)

            counterFab = mOverLayView!!.findViewById(R.id.fabHead)
            counterFab.count = 1

            val layout = mOverLayView!!.findViewById(R.id.layout) as RelativeLayout
            val vto = layout.viewTreeObserver
            vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val width = layout.measuredWidth

                    //To get the accurate middle of the screen we subtract the width of the android floating widget.
                    mWidth = size.x - width

                }
            })


            counterFab.setOnTouchListener(object : OnTouchListener {
                private var initialX: Int = 0
                private var initialY: Int = 0
                private var initialTouchX: Float = 0.toFloat()
                private var initialTouchY: Float = 0.toFloat()

                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {

                            //remember the initial position.
                            initialX = params.x
                            initialY = params.y


                            //get the touch location
                            initialTouchX = event.rawX
                            initialTouchY = event.rawY


                            return true
                        }
                        MotionEvent.ACTION_UP -> {
                            //Add code for launching application and positioning the widget to nearest edge.
                            if (activity_background) {
                                val xDiff = event.rawX - initialTouchX
                                val yDiff = event.rawY - initialTouchY

                                if ((abs(xDiff) < 5) && (abs(yDiff) < 5)) {
                                    val intent =
                                        Intent(this@FloatingWidgetService, FloatingWidgetActivity::class.java).apply {
                                            putExtra("badge_count", counterFab.count)
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        }
                                    val pendingIntent = PendingIntent.getActivity(baseContext, 0, intent, 0)
                                    pendingIntent.send()
                                     stopSelf()
                                }
                            } else {
                                mWindowManager.updateViewLayout(mOverLayView, params)
                            }

                            /*   val midle = mWidth / 2
                               val nearestXWall = (if (params.x >= midle) mWidth else 0).toFloat()
                               params.x = nearestXWall.toInt()*/



                            return true
                        }
                        MotionEvent.ACTION_MOVE -> {


                            val Xdiff = (event.rawX - initialTouchX).roundToInt().toFloat()
                            val Ydiff = (event.rawY - initialTouchY).roundToInt().toFloat()


                            //Calculate the X and Y coordinates of the view.
                            params.x = initialX + Xdiff.toInt()
                            params.y = initialY + Ydiff.toInt()

                            //Update the layout with new X & Y coordinates
                            mWindowManager.updateViewLayout(mOverLayView, params)


                            return true
                        }
                    }
                    return false
                }
            })
        } else {
            counterFab.increase()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.AppTheme)

    }

    override fun onDestroy() {
        mWindowManager.removeViewImmediate(mOverLayView)
        super.onDestroy()
        Log.e("destroy=", "true")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        this.stopSelf()
    }
}
