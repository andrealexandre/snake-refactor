package org.snake.models.basic;

import org.junit.Test;

import static org.junit.Assert.*;

public class RangeTest {

    @Test
    public void contained() {
        // Arrange
        Range range = new Range(0, 10);

        // Act
        boolean contained = range.contained(5);
        boolean belowMin = range.contained(-1);
        boolean notInclusive = range.contained(10);
        boolean aboveMax = range.contained(11);

        // Assert
        assertTrue(contained);
        assertFalse(belowMin);
        assertFalse(notInclusive);
        assertFalse(aboveMax);
    }

    @Test
    public void around() {
        // Arrange
        Range range = new Range(0, 10);

        // Act
        int contained = range.around(5);
        int belowMin = range.around(-1);
        int notInclusive = range.around(10);
        int aboveMax = range.around(11);

        // Assert
        assertEquals(5, contained);
        assertEquals(9, belowMin);
        assertEquals(0, notInclusive);
        assertEquals(0, aboveMax);
    }
}