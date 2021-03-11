package org.snake.models.basic

/**
 * Represents a range with min and max.
 * min is **inclusive**
 * max is **exclusive**
 */
class Range(private val min: Int, private val max: Int) {
    constructor(max: Int) : this(0, max) {}

    fun contained(value: Int): Boolean {
        return min <= value && value < max
    }

    fun around(value: Int): Int {
        return if (value < min) max - 1 else if (value >= max) min else value
    }
}