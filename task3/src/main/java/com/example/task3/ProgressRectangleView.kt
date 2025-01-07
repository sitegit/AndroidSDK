package com.example.task3

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class ProgressRectangleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    private var progress = 0f
    private var currentColor = generateRandomColor()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = Color.LTGRAY
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        paint.color = currentColor
        val fillWidth = width * (progress / 100f)
        canvas.drawRect(0f, 0f, fillWidth, height.toFloat(), paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            progress += 10f
            if (progress > 100f) progress = 0f
            currentColor = generateRandomColor()
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(SUPER_STATE, super.onSaveInstanceState())
            putFloat(PROGRESS, progress)
            putInt(CURRENT_COLOR, currentColor)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState: Parcelable? = null
        if (state is Bundle) {
            progress = state.getFloat(PROGRESS)
            currentColor = state.getInt(CURRENT_COLOR)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                superState = state.getParcelable(SUPER_STATE, Parcelable::class.java)
            } else {
                @Suppress("DEPRECATION")
                superState = state.getParcelable(SUPER_STATE)
            }
        }
        super.onRestoreInstanceState(superState)
    }

    private fun generateRandomColor(): Int {
        return Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    }

    companion object {
        private const val PROGRESS = "progress"
        private const val CURRENT_COLOR = "currentColor"
        private const val SUPER_STATE = "superState"
    }
}