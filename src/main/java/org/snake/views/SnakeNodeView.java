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
    public void draw(Graphics canvas, int width, int height){
        canvas.setColor(SNAKE_NODE_COLOR);
        canvas.fillOval(0, 0, width, height);
        canvas.setColor(Color.BLACK);
        canvas.drawOval(0, 0, width, height);

        if(model.isHead()){
            canvas.setColor(Color.BLUE);
            canvas.fillOval((width)/4, height/4, width/2, height/2);
            canvas.fillOval((width * 3)/4, height/4, width/2, height/2);
        }
    }
}
