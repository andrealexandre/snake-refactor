package org.snake.models.basic

import org.junit.Assert
import org.junit.Test
import snake.models.basic.Point
import snake.models.basic.PointSpace
import snake.models.basic.Range

class PointSpaceTest {
    @Test
    fun around() {
        // Arrange
        val pointSpace = PointSpace(
                Range(0, 10),
                Range(0, 10)
        )

        // Act & Assert
        Assert.assertEquals(Point(5, 5), pointSpace.around(Point(5, 5)))
        Assert.assertEquals(Point(9, 5), pointSpace.around(Point(-1, 5)))
        Assert.assertEquals(Point(0, 5), pointSpace.around(Point(11, 5)))
        Assert.assertEquals(Point(5, 9), pointSpace.around(Point(5, -1)))
        Assert.assertEquals(Point(5, 0), pointSpace.around(Point(5, 11)))
    }
}