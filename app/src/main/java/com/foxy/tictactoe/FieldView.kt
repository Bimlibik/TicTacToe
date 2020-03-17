package com.foxy.tictactoe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class FieldView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    private val COUNT = 3
    private var currentCellIndex = Pair(0, 0)
    private var field = Array(COUNT) {Array(COUNT) {Cell()} }
    private val paint = Paint()
    private val dotPaint = Paint()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // устанавливает одинаковый размер всем сторонам view
        val size = min(measuredHeight, measuredWidth)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initPaint()
        initCell()
        drawVerticalLines(canvas)
        drawHorizontalLines(canvas)
        checkCellStates(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false

        val x = event.x
        val y = event.y
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentCellIndex = getCellIndex(x, y)
                Log.i("TAG", "currentCellIndex = $currentCellIndex")
            }
            MotionEvent.ACTION_UP -> {
                val finalCellIndex = getCellIndex(x, y)
                if (finalCellIndex == currentCellIndex) {
                    Log.i("TAG", "finalCellIndex = $finalCellIndex")
                }
            }
        }
        return true
    }

    private fun checkCellStates(canvas: Canvas) {
        for ((x, cell) in field.withIndex()) {
            for ((y, dot) in cell.withIndex()) {
                if (dot.isEmpty) {
                    continue
                } else {
                    drawDot(canvas, dot.dot, field[x][y])
                }
            }
        }
    }

    private fun drawDot(canvas: Canvas, dot: Dot, cell: Cell) {
        val margin = (width / COUNT) / 4
        val startX = (cell.left + margin).toFloat()
        val startY = (cell.bottom - margin).toFloat()
        canvas.drawText(dot.toString(), startX, startY, dotPaint)
    }

    private fun drawVerticalLines(canvas: Canvas) {
        val cellWidth = (width / COUNT).toFloat()
        for (i in 1 until COUNT) {
            val x = i * cellWidth
            canvas.drawLine(x, 0f, x, height.toFloat(), paint)
        }
    }

    private fun drawHorizontalLines(canvas: Canvas) {
        val cellHeight = (height / COUNT).toFloat()
        for (i in 1 until COUNT) {
            val y = i * cellHeight
            canvas.drawLine(0f, y, width.toFloat(), y, paint)
        }
    }

    private fun getCellIndex(x: Float, y: Float) : Pair<Int, Int> {
        field.forEachIndexed {i, cells ->
            for ((j, cell) in cells.withIndex()) {
                if (cell.contains(x.toInt(), y.toInt())) return Pair(i, j)
            }
        }
        return Pair(-1, -1)
    }

    private fun initPaint() {
        paint.apply {
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

    private fun initCell() {
        val cellSize = width / COUNT
        for (y in 0 until COUNT) {
            for (x in 0 until COUNT) {
                field[x][y] = Cell(left = x * cellSize, top = y * cellSize,
                    right = (x + 1) * cellSize, bottom = (y + 1) * cellSize)
                Log.i("TAG", "y = $y, x = $x, left = ${x*cellSize}, top = ${y*cellSize}, " +
                        "right = ${(x+1)*cellSize}, bottom = ${(y+1)*cellSize}")
            }
        }
    }
}