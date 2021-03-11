package org.snake.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class GameDataDisplay extends JPanel {
	public static final long serialVersionUID = 1L;
	
	public JLabel points;	
	public JLabel tempRats;	
	
	public GameDataDisplay(){
		setLayout(new BorderLayout());
		this.setBorder(new EtchedBorder());
		
		JLabel l = new JLabel("Points: ");
		points = new JLabel("0");		
		tempRats = new JLabel("", JLabel.CENTER);		
		
		l.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		points.setFont(new Font("Comic Sans MS", 0, 12));
		tempRats.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		tempRats.setForeground(Color.BLUE);
		
		JPanel left = new JPanel();
		left.add(l);
		left.add(points);		
		
		
		add(left, BorderLayout.WEST);
		add(tempRats, BorderLayout.CENTER);		
		tempRats.setVisible(false);
	}
}