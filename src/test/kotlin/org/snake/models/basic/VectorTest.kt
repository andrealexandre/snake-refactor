package org.snake.models.basic

import org.junit.Assert
import org.junit.Test
import snake.models.basic.Point
import snake.models.basic.Vector

class VectorTest {

    @Test
    fun isOrigin(): Unit {
        Assert.assertTrue(Vector(0, 0).isOrigin)
        Assert.assertFalse(Vector(1, 1).isOrigin)
        Assert.assertFalse(Vector(-11, -1).isOrigin)
    }

    @Test
    fun addPoint() {
        // Arrange
        val vector = Vector(1, 1)

        // Act
        val point = vector.add(Point(1, 1))

        // Assert
        Assert.assertEquals(Point(2, 2), point)
    }

    @Test
    fun addVector() {
        // Arrange
        val vector = Vector(1, 1)

        // Act
        val result = vector.add(Vector(1, 1))

        // Assert
        Assert.assertEquals(Vector(2, 2), result)
    }
}