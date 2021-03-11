package org.snake.settings

import org.snake.models.Block
import org.snake.models.GameMatrix
import org.snake.models.basic.Point
import org.snake.settings.Player
import org.snake.utils.*
import org.snake.views.BlockView
import java.awt.Font
import java.awt.GridLayout
import java.io.*
import javax.swing.JLabel
import javax.swing.JPanel
import kotlin.system.exitProcess

class FileManager(labyrinthName: String?) {
    private var highScorePath: String
    private var labyrinthPath: String? = null
    private var scores: FList<Player> = FNil

    init {
        if (labyrinthName == null) {
            highScorePath = HIGHSCORE_PATH + NOLABYRINTH + HIGHSCORE_EXT
            labyrinthPath = null
        } else {
            highScorePath = HIGHSCORE_PATH + labyrinthName + HIGHSCORE_EXT
            labyrinthPath = LABYRINTH_PATH + labyrinthName + LABYRINTH_EXT
        }
        loadHighScores()
    }

    fun isNewHighScore(points: Int): Boolean {
        if (points <= 0) {
            return false
        }
        return !scores.take(5).forall { it.score > points }
    }

    fun addNewScore(name: String, points: Int) {
        scores.insertBefore(Player(name, points)) { it.score < points }
        saveHighScores()
    }

    private fun saveHighScores() {
        PrintWriter(highScorePath).use { pw ->
            scores.foreach { pw.println(it) }
        }
    }

    private fun loadHighScores() {
        try {
            BufferedReader(FileReader(highScorePath)).use { br ->
                br.readLines().forEach { line ->
                    scores += Player(
                            line.split(Player.Companion.SEPARATOR).toTypedArray().get(0),
                            line.split(" - ").toTypedArray()[1].toInt()
                    )
                }
            }
        } catch (e: FileNotFoundException) {
            val f = File(highScorePath)
            try {
                f.createNewFile()
            } catch (ioe: IOException) {
            }
        } catch (e: IOException) {
            exitProcess(0)
        }
    }

    fun loadLabyrinth(c: GameMatrix) {
        if (labyrinthPath != null) {
            loadLabyrinth(c, labyrinthPath!!)
        }
    }

    val displayHighScore: JPanel
        get() {
            val d = JPanel()
            d.layout = GridLayout(6, 1)
            var s = ""
            val path = labyrinthPath
            if (path != null) {
                val aux = path.toCharArray()
                for (i in path.lastIndexOf(".") - 1 downTo 0) {
                    if (aux[i] == '\\') break
                    s = aux[i].toString() + s
                }
            } else {
                s = "No Labyrinth"
            }
            val l = JLabel(s, JLabel.CENTER)
            l.font = Font("Comic Sans MS", Font.BOLD, 14)
            d.add(l)

            scores.mapIndexed { index: Int, player: Player ->
                JLabel("${index + 1}* - ${player.score}", JLabel.CENTER)
            }.paddingIndexed(5) { index: Int ->
                JLabel("${index + 1}* -  ------------------", JLabel.CENTER)
            }.foreach { d.add(it) }

            return d
        }

    companion object {
        const val HIGHSCORE_PATH = "high-scores/"
        const val HIGHSCORE_EXT = ".high"
        const val LABYRINTH_PATH = "labyrinths/"
        const val LABYRINTH_EXT = ".labyrinth"
        const val NOLABYRINTH = "noLabyrinth"

        fun loadLabyrinth(c: GameMatrix, labyrinthPath: String) {
            BufferedReader(FileReader(labyrinthPath)).use { br ->
                var stringBuilder: StringBuilder = StringBuilder()
                br.readLines().forEach { line ->
                    val xy = line.split(",").toTypedArray()
                    val point = Point(xy[0].toInt(), xy[1].toInt())
                    val b = BlockView(Block(point))
                    c.setView(b, point.x, point.y)
                }
            }
        }
    }
}