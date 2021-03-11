package org.snake.views;

import org.snake.models.SnakeNode;

import java.awt.*;

public class SnakeNodeView implements FigureView {
    private static final Color SNAKE_NODE_COLOR = new Color(105, 210, 0);

    private final SnakeNode model;

    public SnakeNodeView(SnakeNode model) {
        this.model = model;
    }

    public SnakeNode getModel() {
        return model;
    }

    @Override
    public boolean isEmptySpace() {
        return false;
    }

    @Override
    public void draw(Graphics canvas, int x, int y, int width, int height) {
        canvas.setColor(SNAKE_NODE_COLOR);
        canvas.fillOval(x, y, width, height);
        canvas.setColor(Color.BLACK);
        canvas.drawOval(x, y, width, height);

        if(model.isHead()){
            canvas.setColor(Color.BLUE);
            canvas.fillOval(x + (width)/4, y + height/4, width/2, height/2);
            canvas.fillOval(x +(width * 3)/4, y + height/4, width/2, height/2);
        }
    }

}
