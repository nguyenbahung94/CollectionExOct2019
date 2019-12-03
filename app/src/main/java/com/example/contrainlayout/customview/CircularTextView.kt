package com.example.contrainlayout.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

class CircularTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var circlePaint: Paint? = null

    init {
        circlePaint = Paint()
        circlePaint?.color = Color.RED
        circlePaint?.flags = Paint.ANTI_ALIAS_FLAG
    }

    override fun onDraw(canvas: Canvas?) {
        val h = this.height
        val w = this.width

        val diameter = if (h > w) h else w
        val radius = diameter / 2
        this.height = diameter
        this.width = diameter

        canvas?.drawCircle(
            (diameter / 2).toFloat(),
            (diameter / 2).toFloat(),
            radius.toFloat(),
            circlePaint!!
        )
        super.onDraw(canvas)
    }
}