package org.snake.settings

class Player(private val name: String, val score: Int) {
    override fun toString(): String {
        return name + SEPARATOR + score
    }

    companion object {
        const val SEPARATOR = " - "
    }
}