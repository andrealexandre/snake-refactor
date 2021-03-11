package org.snake.models.basic;

import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void testToString() {
        // Arrange
        Point point = new Point(1, 1);

        // Act & Assert
        assertEquals("Point{x=1, y=1}", point.toString());
    }
}