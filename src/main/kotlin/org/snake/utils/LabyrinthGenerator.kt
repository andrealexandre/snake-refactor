package org.snake.utils

import java.io.FileNotFoundException
import java.io.IOException
import java.io.PrintWriter

class LabyrinthGenerator(pathOut: String?) {

    var pw: PrintWriter = PrintWriter(pathOut)

    fun close() {
        pw.close()
    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int) {
        var x1 = x1
        var y1 = y1
        while (x1 != x2 || y1 != y2) {
            pw.println("$x1,$y1")
            if (x1 < x2) {
                x1++
            } else {
                if (x1 > x2) {
                    x1--
                }
            }
            if (y1 < y2) {
                y1++
            } else {
                if (y1 > y2) {
                    y1--
                }
            }
        }
    }

    companion object {
        @Throws(FileNotFoundException::class, IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val l = LabyrinthGenerator("D:\\ISEL\\2� Semestre\\POO\\Trabalho 3\\Snake\\files\\labyrinths\\n4.labyrinth")
            l.drawLine(10, 10, 10, 20)
            l.drawLine(20, 10, 20, 20)
            l.close()
        }
    }

}