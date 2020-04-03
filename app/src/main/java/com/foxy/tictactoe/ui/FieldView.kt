package com.foxy.tictactoe.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
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

    private var cellCount = 3
    private var dotInfo = mutableListOf<Cell>()
    private lateinit var callback: FieldCallback
    private var hasAnimate = false

    private val winLine = Path()
    private val linePaint = Paint()
    private val dotPaint = Paint()
    private val animatePaint = Paint()

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

        if (hasAnimate) {
            canvas.drawPath(winLine, animatePaint)
        }
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

    fun reset() {
        dotInfo.clear()
        winLine.reset()
        hasAnimate = false
        isEnabled = true
        invalidate()
    }

    fun changeDotInCell(newDotInfo: MutableList<Cell>) {
        dotInfo.clear()
        dotInfo.addAll(newDotInfo)
        invalidate()
    }

    fun setCellCount(cellCount: Int) {
        this.cellCount = cellCount
    }

    fun setupFieldCallback(callback: FieldCallback) {
        this.callback = callback
    }

    fun animateWin(centerX1: Float, centerY1: Float, centerX2: Float, centerY2: Float) {
        winLine.reset()
        winLine.moveTo(centerX1, centerY1)
        winLine.lineTo(centerX2, centerY2)
        hasAnimate = true

        val animator = ValueAnimator.ofFloat(1f, 0f)
        animator.duration = 600
        animator.addUpdateListener { onAnimationUpdate(animator) }
        animator.start()
    }

    private fun onAnimationUpdate(animator: ValueAnimator) {
        val measure = PathMeasure(winLine, false)
        val pathLength = measure.length
        val phase = (pathLength * (animator.animatedValue as Float))
        animatePaint.pathEffect = DashPathEffect(floatArrayOf(pathLength, pathLength), phase)
        invalidate()
    }

    private fun checkCellStates(canvas: Canvas) {
        for (cell in dotInfo) {
            drawDot(canvas, cell)
        }
    }

    private fun drawDot(canvas: Canvas, cell: Cell) {
        val margin = (width / cellCount) / 4
        val startX = (cell.left + margin).toFloat()
        val startY = (cell.bottom - margin).toFloat()
        canvas.drawText(cell.dot.toString(), startX, startY, dotPaint)
    }

    private fun drawVerticalLines(canvas: Canvas) {
        val cellWidth = (width / cellCount).toFloat()
        for (i in 1 until cellCount) {
            val x = i * cellWidth
            canvas.drawLine(x, 0f, x, height.toFloat(), linePaint)
        }
    }

    private fun drawHorizontalLines(canvas: Canvas) {
        val cellHeight = (height / cellCount).toFloat()
        for (i in 1 until cellCount) {
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
            val margin = (width / cellCount) / 4
            textSize = resources.displayMetrics.scaledDensity * margin
        }

        animatePaint.apply {
            color = Color.RED
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = resources.displayMetrics.density * 5
        }
    }
}