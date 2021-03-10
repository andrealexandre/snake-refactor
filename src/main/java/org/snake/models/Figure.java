package org.snake.models;

import org.snake.models.basic.Point;

public interface Figure {
    boolean isEmptySpace();
    Point getPoint();
    void setPoint(Point point);
}