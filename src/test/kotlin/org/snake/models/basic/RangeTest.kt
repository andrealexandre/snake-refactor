package org.snake.models.basic

import org.junit.Assert
import org.junit.Test
import snake.models.basic.Range

class RangeTest {
    @Test
    fun contained() {
        // Arrange
        val range = Range(0, 10)

        // Act
        val contained = range.contained(5)
        val belowMin = range.contained(-1)
        val notInclusive = range.contained(10)
        val aboveMax = range.contained(11)

        // Assert
        Assert.assertTrue(contained)
        Assert.assertFalse(belowMin)
        Assert.assertFalse(notInclusive)
        Assert.assertFalse(aboveMax)
    }

    @Test
    fun around() {
        // Arrange
        val range = Range(0, 10)

        // Act
        val contained = range.around(5)
        val belowMin = range.around(-1)
        val notInclusive = range.around(10)
        val aboveMax = range.around(11)

        // Assert
        Assert.assertEquals(5, contained.toLong())
        Assert.assertEquals(9, belowMin.toLong())
        Assert.assertEquals(0, notInclusive.toLong())
        Assert.assertEquals(0, aboveMax.toLong())
    }
}