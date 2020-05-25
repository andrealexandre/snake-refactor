package org.snake.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.snake.settings.GameConfiguration;


public class GameMenu extends JFrame {
	public static final long serialVersionUID = 1L;
	
	public GameConfiguration config;
	
	public GameMenu(String menuTitle, GameConfiguration config){
		super(menuTitle);
		this.config = config;
	}

	public void start() {
		setLayout(new BorderLayout());

		JButton start = new JButton("Start");
		JButton options = new JButton("Options");
		JButton exit = new JButton("Exit");

		start.addActionListener(event -> {
			this.setVisible(false);
			new GameWindow(this, config);
		});
		options.addActionListener(event -> {
			new OptionsFrame(this);
		});
		exit.addActionListener(event -> {
			System.exit(0);
		});

		add(start, BorderLayout.NORTH);
		add(options, BorderLayout.CENTER);
		add(exit, BorderLayout.SOUTH);

		setSize(250, 110);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

}