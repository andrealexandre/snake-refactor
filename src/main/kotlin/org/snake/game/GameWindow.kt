package org.snake.game

import com.google.common.collect.Lists
import org.snake.models.Game
import org.snake.models.Game.OnChangeListener
import org.snake.models.GameMatrix
import org.snake.models.Rat
import org.snake.settings.FileManager
import org.snake.settings.GameConfiguration
import java.awt.BorderLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame

class GameWindow(windowTitle: String?, callerMenu: GameMenu, config: GameConfiguration) : JFrame(windowTitle) {
    var fileManager // Game window shouldn't care about this
            : FileManager
    private val callerMenu // GameMenu should be mentioned here, we are creating direct cyclic dependencies
            : GameMenu
    var display = GameDataDisplay() // Game window shouldn't instantiate this
    private val gameMatrix = GameMatrix(WIDTH, HEIGHT)
    private val board = Board(gameMatrix) // Game window shouldn't instantiate this
    private val rat = Rat(gameMatrix, display) // Game window shouldn't instantiate this
    private val game: Game
    private var gameState = GameState.PLAYING
    val currentPoint: Int
        get() = game.points

    fun getGameState(): GameState {
        return gameState
    }

    fun restart() {
        gameState = GameState.PLAYING
        game.restart()
        rat.restart()
    }

    fun close() {
        callerMenu.isVisible = true
        dispose()
    }

    fun setGameState(code: GameState) {
        if (code == GameState.PLAYING) {
            game.startSnake()
        }
        gameState = code
    }

    fun gameover() {
        updateGameState(GameState.GAMEOVER)
    }

    fun callDialog() {
        game.stopSnake()
        GameDialog(this)
    }

    private inner class KeyboardInterface : KeyAdapter() {
        override fun keyPressed(k: KeyEvent) {
            val code = k.keyCode
            val keys: List<Int> = Lists.newArrayList(
                    KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT)
            if (keys.contains(code) && !(gameState == GameState.PAUSED || gameState == GameState.GAMEOVER)) {
                game.moveSnake(code)
            } else {
                if (code == KeyEvent.VK_ESCAPE) {
                    updateGameState(GameState.PAUSED)
                }
            }
        }
    }

    private fun updateGameState(paused: GameState) {
        gameState = paused
        callDialog()
    }

    companion object {
        const val serialVersionUID = 1L
        const val WIDTH = 30
        const val HEIGHT = 35
    }

    init {
        isResizable = false
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        addKeyListener(KeyboardInterface())
        this.callerMenu = callerMenu
        fileManager = FileManager(config.labyrinthPath)
        fileManager.loadLabyrinth(gameMatrix) // side effects leak
        game = Game(object : OnChangeListener {
            override fun onPointsChange(points: Int) {
                display.points.text = points.toString()
            }

            override fun onGameOver() {
                gameover()
            }

            override fun onGameMatrixUpdate() {
                board.repaint()
            }
        }, gameMatrix, rat, config) // Game window shouldn't instantiate this
        layout = BorderLayout()
        add(display, BorderLayout.NORTH)
        add(board, BorderLayout.SOUTH)
        pack()
        isVisible = true
    }
}