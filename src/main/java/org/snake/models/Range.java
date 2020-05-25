package org.snake.models;

/**
 * Represents a range with min and max.
 * min is <strong>inclusive</strong>
 * max is <strong>exclusive</strong>
 */
public class Range {
    private final int min;
    private final int max;

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public Range(int max) {
        this(0, max);
    }

    public boolean contained(int value) {
        return min <= value && value < max;
    }

    public int around(int value) {
        if (value < min) return max - 1;
        else if (value >= max) return min;
        else return value;
    }
}