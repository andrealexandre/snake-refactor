package org.snake.models;

public class Block extends AbstractFigure {

    public Block(Point point) {
        super(point);
    }

    @Override
    public boolean isEmptySpace() {
        return false;
    }

}