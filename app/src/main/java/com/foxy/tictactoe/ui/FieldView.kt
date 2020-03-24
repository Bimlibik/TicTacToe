package com.foxy.tictactoe.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.foxy.tictactoe.data.Cell
import com.foxy.tictactoe.utils.FieldCallback
import kotlin.math.min

class FieldView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    private val COUNT = 3
    private var dotInfo = mutableListOf<Cell>()
    private lateinit var callback: FieldCallback
    private val linePaint = Paint()
    private val dotPaint = Paint()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // выбирает минимальную длину экрана (ширина / высота) и устанавливает ее как размер
        // всех сторон view
        Log.i("TAG", "onMeasure")
        val size = min(measuredHeight, measuredWidth)
        setMeasuredDimension(size, size)
        callback.saveFieldSize(size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initPaint()
        drawVerticalLines(canvas)
        drawHorizontalLines(canvas)
        checkCellStates(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false

        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                callback.saveCurrentCellIndex(x.toInt(), y.toInt())
            }
            MotionEvent.ACTION_UP -> {
                callback.onCellClick(x.toInt(), y.toInt())
            }
        }
        return true
    }

    fun changeDotInCell(newDotInfo: MutableList<Cell>) {
        dotInfo.clear()
        dotInfo.addAll(newDotInfo)
        invalidate()
    }

    fun setupFieldCallback(callback: FieldCallback) {
        this.callback = callback
    }

    private fun checkCellStates(canvas: Canvas) {
        for (cell in dotInfo) {
            drawDot(canvas, cell)
        }
    }

    private fun drawDot(canvas: Canvas, cell: Cell) {
        val margin = (width / COUNT) / 4
        val startX = (cell.left + margin).toFloat()
        val startY = (cell.bottom - margin).toFloat()
        canvas.drawText(cell.dot.toString(), startX, startY, dotPaint)
    }

    private fun drawVerticalLines(canvas: Canvas) {
        val cellWidth = (width / COUNT).toFloat()
        for (i in 1 until COUNT) {
            val x = i * cellWidth
            canvas.drawLine(x, 0f, x, height.toFloat(), linePaint)
        }
    }

    private fun drawHorizontalLines(canvas: Canvas) {
        val cellHeight = (height / COUNT).toFloat()
        for (i in 1 until COUNT) {
            val y = i * cellHeight
            canvas.drawLine(0f, y, width.toFloat(), y, linePaint)
        }
    }

    private fun initPaint() {
        linePaint.apply {
            color = Color.BLACK
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = resources.displayMetrics.density * 5
        }

        dotPaint.apply {
            color = Color.BLACK
            isAntiAlias = true
            textSize = resources.displayMetrics.scaledDensity * 70
        }
    }
}