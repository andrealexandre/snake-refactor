package org.snake.models;

import org.snake.models.basic.Point;
import org.snake.views.FigureView;
import org.snake.views.GrassView;

import java.util.Arrays;

public class GameMatrix {

    private final int width;
    private final int height;
    private final FigureView[][] matrix;

    public GameMatrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new FigureView[width][height];
        for (FigureView[] figureViews : matrix) {
            Arrays.fill(figureViews, GrassView.INSTANCE);
        }
    }

    public FigureView get(int x, int y) {
        return matrix[x][y];
    }

    public FigureView get(Point point) {
        return get(point.x, point.y);
    }

    public void setView(FigureView view, Point position) {
        setView(view, position.x, position.y);
    }

    public void setView(FigureView view, int x, int y) {
        matrix[x][y] = view == null ? GrassView.INSTANCE : view;
    }

    public void removeView(Point point) {
        removeView(point.x, point.y);
    }

    public void removeView(int x, int y) {
        matrix[x][y] = GrassView.INSTANCE;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public boolean isEmpty(int x, int y) {
        return matrix[x][y].isEmptySpace();
    }

    public boolean isEmpty(Point newPoint) {
        return isEmpty(newPoint.x, newPoint.y);
    }
}
