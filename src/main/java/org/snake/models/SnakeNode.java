package org.snake.models;

import org.snake.models.basic.Point;

public class SnakeNode  {
    private Point point;
    private boolean isHead;

    public SnakeNode(Point point, boolean isHead) {
        this.point = point;
        this.isHead = isHead;
    }

    public Point getPoint() {
        return point;
    }
    public void setPoint(Point point) {
        this.point = point;
    }

    public boolean isHead() {
        return isHead;
    }
    public void asHead(){
        isHead = true;
    }
    public void asTail(){
        isHead = false;
    }

}
