package org.snake.game;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.snake.elements.Cell;

public class Board extends JPanel{
	public static final long serialVersionUID = 1L;
	public static final Color BOARD_DEFAULT_COLOR = new Color(0, 128, 0);
	
	private Cell[][] board;

	public static final int GRID_WIDTH = 30;
	public static final int GRID_HEIGHT = 35;	

	public Board(){
		super();
		this.setBackground(BOARD_DEFAULT_COLOR);
		setBorder(new EtchedBorder());				
		setLayout(new GridLayout(GRID_HEIGHT, GRID_WIDTH));	
		
		board = new Cell[GRID_WIDTH][GRID_HEIGHT];
				
		for(int y = 0; y < GRID_HEIGHT; y++){
			for(int x = 0; x < GRID_WIDTH; x++){				
				Cell c = new Cell();				
				board[x][y] = c;				
				this.add(c);	
			}	
		}
	}
	
	public Cell[][] getBoard(){return board;}
	
	public void clearLabyrinth(){
		for(int y = 0; y < GRID_HEIGHT; y++){
			for(int x = 0; x < GRID_WIDTH; x++){				
				board[x][y].removeFigure();			
			}	
		}
	}
}