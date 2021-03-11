package org.snake.views;

import org.snake.models.Block;

import java.awt.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class BlockView implements FigureView {

	private Block model;

	public BlockView(Block model) {
		this.model = checkNotNull(model);
	}

	@Override
	public boolean isEmptySpace() {
		return model.isEmptySpace();
	}

	@Override
	public void draw(Graphics canvas, int x, int y){
		canvas.setColor(Color.lightGray);
		canvas.fill3DRect(0, 0, x, y, true);	
	}

}
