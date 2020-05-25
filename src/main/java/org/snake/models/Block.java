package org.snake.models;

import org.snake.models.basic.Point;

public class Block extends AbstractFigure {

    public Block(Point point) {
        super(point);
    }

    @Override
    public boolean isEmptySpace() {
        return false;
    }

}