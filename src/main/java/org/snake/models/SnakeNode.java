package org.snake.elements;

import java.awt.*;

public class SnakeNode implements Figure {
    private static final Color SNAKE_NODE_COLOR = new Color(105, 210, 0);

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

    public void asHead(){
        isHead = true;
    }
    public void asTail(){
        isHead = false;
    }

    public void draw(Graphics canvas, int width, int height){
        canvas.setColor(SNAKE_NODE_COLOR);
        canvas.fillOval(0, 0, width, height);
        canvas.setColor(Color.BLACK);
        canvas.drawOval(0, 0, width, height);

        if(isHead){
            canvas.setColor(Color.BLUE);
            canvas.fillOval((width)/4, height/4, width/2, height/2);
            canvas.fillOval((width * 3)/4, height/4, width/2, height/2);
        }
    }
}
