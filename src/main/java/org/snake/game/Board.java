package org.snake.game;

import org.snake.models.GameMatrix;
import org.snake.views.FigureView;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class Board extends JPanel {
	public static final long serialVersionUID = 1L;
	public static final Color BOARD_DEFAULT_COLOR = new Color(0, 128, 0);
	public static final int GRID_THICKNESS = 25;

	private final GameMatrix matrix;

	public Board(GameMatrix matrix) {
		super();
		this.matrix = matrix;
		this.setBackground(BOARD_DEFAULT_COLOR);
		setBorder(new EtchedBorder());
		setLayout(null);
		setSize(GRID_THICKNESS * matrix.width(), GRID_THICKNESS * matrix.height());
		setPreferredSize(new Dimension(GRID_THICKNESS * matrix.width(), GRID_THICKNESS * matrix.height()));
	}

	@Override
	protected void paintComponent(Graphics canvas) {
		super.paintComponent(canvas);
		for (int y = 0; y < matrix.height(); y++) {
			for (int x = 0; x < matrix.width(); x++) {
				matrix.get(x, y).draw(canvas, x * GRID_THICKNESS, y * GRID_THICKNESS, GRID_THICKNESS, GRID_THICKNESS);
			}
		}
	}

	public void clearLabyrinth(){
		for(int y = 0; y < matrix.height(); y++){
			for(int x = 0; x < matrix.width(); x++){
				matrix.removeView(x, y);
			}	
		}
	}
}