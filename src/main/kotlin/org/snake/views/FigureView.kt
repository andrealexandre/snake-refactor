package org.snake.views

import java.awt.Graphics

interface FigureView {
    val isEmptySpace: Boolean
    fun draw(canvas: Graphics, x: Int, y: Int, width: Int, height: Int)
}