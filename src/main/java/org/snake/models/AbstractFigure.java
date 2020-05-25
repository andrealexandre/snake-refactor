package org.snake.models;

public abstract class AbstractFigure implements Figure {

    private Point point;

    protected AbstractFigure(Point point) {
        this.point = point;
    }

    @Override
    public Point getPoint() {
        return point;
    }
    @Override
    public void setPoint(Point point) {
        this.point = point;
    }
}