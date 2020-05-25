package org.snake.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.snake.settings.GameConfiguration;


public class GameMenu extends JFrame implements ActionListener{
	public static final long serialVersionUID = 1L;
	
	public GameConfiguration config;
	
	public GameMenu(){
		super("Snake Game Menu");
		
		config = new GameConfiguration();
		
		setLayout(new BorderLayout());
		
		JButton start = new JButton("Start");
		JButton options = new JButton("Options");
		JButton exit = new JButton("Exit");
		
		start.addActionListener(this);
		options.addActionListener(this);
		exit.addActionListener(this);
		
		add(start, BorderLayout.NORTH);
		add(options, BorderLayout.CENTER);
		add(exit, BorderLayout.SOUTH);		
				
		setSize(250, 110);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
		
	public void actionPerformed(ActionEvent a){
		String s = a.getActionCommand();
		
		if(s.equals("Start")){
			this.setVisible(false);			
			new GameWindow(this, config);
		}else{
			if(s.equals("Options")){				
				new OptionsFrame(this);				
			}else{
				if(s.equals("Exit")){
					System.exit(0);
				}
			}
		}
	}
	
	public static void main(String[] args){
		new GameMenu();
	}
}