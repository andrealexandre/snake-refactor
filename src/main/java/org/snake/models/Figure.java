package org.snake.models;

public interface Figure {
    boolean isEmptySpace();
    Point getPoint();
    void setPoint(Point point);
}