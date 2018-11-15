package com.example.app.drawtest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.WHITE
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View


class MView(context: Context) : View(context) {
    var point = mutableListOf<Point>()
    var thick: Float = 10f
    var eraserThick: Float = 10f
    var paint = Paint() // 화면에 그려줄 도구를 셋팅하는 객체
    var color = "#000000"
    var tool = "pen"
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        setBackgroundColor(WHITE) // 배경색을 지정

        point.forEachIndexed { i, afterPoint ->
            if (afterPoint.move) {
                paint.strokeWidth = afterPoint.thick
                paint.color = Color.parseColor(afterPoint.color)
                val beforePoint = point[i - 1]
                canvas?.drawLine(beforePoint.x, beforePoint.y, afterPoint.x, afterPoint.y, paint)
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (tool == "pen") point.add(Point(event.x, event.y, false, thick, color))
                else point.add(Point(event.x, event.y, false, eraserThick,"#ffffff"))
                postInvalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (tool == "pen") point.add(Point(event.x, event.y, true, thick, color))
                else point.add(Point(event.x, event.y, true, eraserThick,"#ffffff"))
                postInvalidate()
                return true
            }

        }
        postInvalidate()
        return true
    }

}

class Point(val x: Float, val y: Float, val move: Boolean, val thick: Float, val color: String)
