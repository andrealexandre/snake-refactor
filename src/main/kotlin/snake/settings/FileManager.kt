package snake.settings

import snake.models.Block
import snake.models.GameMatrix
import snake.models.basic.Point
import snake.utils.*
import snake.views.BlockView
import java.awt.Font
import java.awt.GridLayout
import java.io.*
import javax.swing.JLabel
import javax.swing.JPanel

class FileManager(labyrinthName: String?) {
    private var highScorePath: String
    private val labyrinthPath: String?
    private var scores: FList<Player> = FNil
    private val separator = ","
    private val topHighscoreCount = 5

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

    fun isNewHighScore(points: Int): Boolean =
        if (points <= 0) false
        else !scores.take(topHighscoreCount).forone { points > it.score }

    fun addNewPlayerScore(player: Player) {
        scores = scores.insertBefore(player) { it.score < player.score }
        saveHighScores()
    }

    private fun ensureHighscoreFileExists(path: String): File = File(path).apply { createNewFile() }

    private fun saveHighScores() {
        PrintWriter(ensureHighscoreFileExists(highScorePath)).use { pw ->
            scores.foreach {
                pw.print(it.name)
                pw.print(separator)
                pw.print(it.score)
                pw.println()
            }
        }
    }

    private fun loadHighScores() {
        FileReader(ensureHighscoreFileExists(highScorePath)).use { reader ->
            reader.readLines().forEach { line ->
                val array = line.split(separator).toTypedArray()
                scores += Player(array[0], array[1].toInt())
            }
        }
    }

    fun loadLabyrinth(c: GameMatrix) {
        if (labyrinthPath != null) {
            loadLabyrinth(c, labyrinthPath)
        }
    }

    fun displayHighScore(): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(6, 1)

        val path = labyrinthPath
        val text = path?.substringBefore(".") ?: "No Labyrinth"

        val l = JLabel(text, JLabel.CENTER)
        l.font = Font("Comic Sans MS", Font.BOLD, 14)
        panel.add(l)

        scores.mapIndexed { index: Int, player: Player -> "${index + 1}* - ${player.name} - ${player.score}" }
                .paddingIndexed(5) { index: Int -> "${index + 1}* -  ------------------" }
                .map { JLabel(it, JLabel.CENTER) }
                .foreach { panel.add(it) }

        return panel
    }

    companion object {
        const val HIGHSCORE_PATH = "high-scores/"
        const val HIGHSCORE_EXT = ".high"
        const val LABYRINTH_PATH = "labyrinths/"
        const val LABYRINTH_EXT = ".labyrinth"
        const val NOLABYRINTH = "noLabyrinth"

        fun loadLabyrinth(c: GameMatrix, labyrinthPath: String) {
            FileReader(labyrinthPath).use { reader ->
                reader.readLines().forEach { line ->
                    val xy = line.split(",").toTypedArray()
                    val point = Point(xy[0].toInt(), xy[1].toInt())
                    val b = BlockView(Block(point))
                    c.setView(b, point.x, point.y)
                }
            }
        }
    }
}