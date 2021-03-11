package org.snake.models.basic;

import org.junit.Test;

import static org.junit.Assert.*;

public class PointSpaceTest {

    @Test
    public void around() {
        // Arrange
        PointSpace pointSpace = new PointSpace(
                new Range(0, 10),
                new Range(0, 10)
        );

        // Act & Assert
        assertEquals(new Point(5, 5), pointSpace.around(new Point(5, 5)));
        assertEquals(new Point(9, 5), pointSpace.around(new Point(-1, 5)));
        assertEquals(new Point(0, 5), pointSpace.around(new Point(11, 5)));
        assertEquals(new Point(5, 9), pointSpace.around(new Point(5, -1)));
        assertEquals(new Point(5, 0), pointSpace.around(new Point(5, 11)));
    }
}