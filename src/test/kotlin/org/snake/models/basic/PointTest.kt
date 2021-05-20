package org.snake.models.basic

import org.junit.Assert
import org.junit.Test
import snake.models.basic.Point

class PointTest {
    @Test
    fun testToString() {
        // Arrange
        val point = Point(1, 1)

        // Act & Assert
        Assert.assertEquals("Point{x=1, y=1}", point.toString())
    }
}