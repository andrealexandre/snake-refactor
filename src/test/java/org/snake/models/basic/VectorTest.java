package org.snake.models.basic;

import org.junit.Test;

import javax.xml.bind.util.ValidationEventCollector;

import static org.junit.Assert.*;

public class VectorTest {

    @Test
    public void isOrigin() {
        assertTrue(new Vector(0, 0).isOrigin());
        assertFalse(new Vector(1, 1).isOrigin());
        assertFalse(new Vector(-11, -1).isOrigin());
    }

    @Test
    public void addPoint() {
        // Arrange
        Vector vector = new Vector(1, 1);

        // Act
        Point point = vector.add(new Point(1, 1));

        // Assert
        assertEquals(new Point(2, 2), point);
    }

    @Test
    public void addVector() {
        // Arrange
        Vector vector = new Vector(1, 1);

        // Act
        Vector result = vector.add(new Vector(1, 1));

        // Assert
        assertEquals(new Vector(2, 2), result);
    }
}