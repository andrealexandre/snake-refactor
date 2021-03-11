package org.snake.models;

import org.snake.models.basic.Point;
import org.snake.models.basic.PointSpace;
import org.snake.models.basic.Range;
import org.snake.models.basic.Vector;
import org.snake.settings.GameConfiguration;
import org.snake.settings.SnakeSpeed;
import org.snake.utils.FCons;
import org.snake.utils.FList;
import org.snake.utils.FNil;
import org.snake.views.SnakeNodeView;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

import static com.google.common.collect.Lists.newLinkedList;

public class Game {
    private final OnChangeListener onChangeListener;

    private final GameMatrix matrix;
    private final Rat rat;
    private final PointSpace pointSpace;

    private FList<SnakeNodeView> snakeList;
    private final Timer timer;

    private Vector direction;
    private final int snakeSpeed;
    private int points;

    private static final int UP = KeyEvent.VK_UP;
    private static final int DOWN = KeyEvent.VK_DOWN;
    private static final int LEFT = KeyEvent.VK_LEFT;
    private static final int RIGHT = KeyEvent.VK_RIGHT;
    private static final int ADDITION_POINTS = 5;

    public Game(OnChangeListener onChangeListener, GameMatrix matrix, Rat rat, GameConfiguration config) {
        this.matrix = matrix;
        this.onChangeListener = onChangeListener;
        this.rat = rat;
        this.pointSpace = new PointSpace(new Range(matrix.width()), new Range(matrix.height()));

        direction = new Vector(0, 0);
        if (config != null) {
            snakeSpeed = config.getSnakeSpeed().snakeSpeed;
        } else {
            snakeSpeed = SnakeSpeed.MEDIUM.snakeSpeed;
        }

        timer = new Timer(snakeSpeed, event -> update());

        Random rn = new Random();
        int x = rn.nextInt(matrix.width());
        int y = rn.nextInt(matrix.height());

        final SnakeNodeView snakeNode = new SnakeNodeView(new SnakeNode(new Point(x, y), true));
        snakeList = new FCons<>(snakeNode, FNil.instance());
        matrix.setView(snakeNode, x, y);
    }

    public void update() {
        SnakeNodeView last = snakeList.getLast();

        try {
            final Point point = last.getModel().getPoint();
            matrix.removeView(point);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        SnakeNodeView head = snakeList.head();
        head.getModel().asTail();
        final Point point = head.getModel().getPoint();

        if (rat.inPosition(point)) {
            if (rat.isTimed()) {
                points = points + ADDITION_POINTS + (SnakeSpeed.EASY.snakeSpeed - snakeSpeed) * rat.getNumberOfRats();
            } else {
                grow();
                points = points + ADDITION_POINTS + SnakeSpeed.EASY.snakeSpeed - snakeSpeed;
            }
            onChangeListener.onPointsChange(points);
            rat.eatRat();
        }

        final Point newPoint = pointSpace.around(direction.add(point));

        if (!matrix.isEmpty(newPoint)) {
            timer.stop();
            onChangeListener.onGameOver();
            return;
        }

        last.getModel().setPoint(newPoint);

        matrix.setView(last, newPoint);
        snakeList = new FCons<>(last, snakeList);
        last.getModel().asHead();
        snakeList = snakeList.removeLast();
        onChangeListener.onGameMatrixUpdate();
    }

    public void restart() {
        snakeList.foreach(v -> matrix.removeView(v.getModel().getPoint()));

        timer.setDelay(snakeSpeed);
        direction = new Vector();
        points = 0;
        onChangeListener.onPointsChange(points);
        snakeList = FNil.instance();

        Random rn = new Random();
        int x = rn.nextInt(this.matrix.width());
        int y = rn.nextInt(this.matrix.height());

        SnakeNodeView sv = new SnakeNodeView(new SnakeNode(new Point(x, y), true));
        snakeList = new FCons<>(sv, snakeList);
        matrix.setView(sv, x, y);
    }

    public void stopSnake() {
        timer.stop();
    }

    public void startSnake() {
        timer.start();
    }

    public int getPoints() {
        return points;
    }

    public void moveSnake(int keyCode) {
        if (direction.isOrigin()) {
            timer.start();
        }

        Vector newDirection = null;

        switch (keyCode) {
            case UP:
                newDirection = new Vector(0, -1);
                break;
            case DOWN:
                newDirection = new Vector(0, +1);
                break;
            case LEFT:
                newDirection = new Vector(-1, 0);
                break;
            case RIGHT:
                newDirection = new Vector(+1, 0);
                break;
        }

        if (newDirection != null) {
            Vector result = direction.add(newDirection);
            if (!result.isOrigin()) {
                direction = newDirection;
            }
        }
    }

    public void grow() {
        SnakeNodeView last = snakeList.getLast();
        final Point newPoint = pointSpace.around(direction.add(last.getModel().getPoint()));

        SnakeNodeView sv = new SnakeNodeView(new SnakeNode(newPoint, false));
        snakeList = new FCons<>(sv, snakeList);
    }

    public interface OnChangeListener {
        void onPointsChange(int points);

        void onGameOver();

        void onGameMatrixUpdate();
    }
}