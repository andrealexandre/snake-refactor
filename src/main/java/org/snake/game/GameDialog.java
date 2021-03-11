package org.snake.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GameDialog extends JDialog implements ActionListener{
	public static final long serialVersionUID = 1L;
	
	public GameDialog(GameWindow j){
		super(j);
		j.setVisible(false);
		
		this.setResizable(false);
		setLayout(new BorderLayout());
		
		//Buttons Panel************
		JPanel j1 = new JPanel();
		j1.setLayout(new FlowLayout());
		
		if(j.getGameState() == (GameState.PAUSED)){
			setTitle("Pause");
			JButton play = new JButton("Play");										
			play.addActionListener(this);				
			j1.add(play);
		}else{
			if(j.getGameState() == (GameState.GAMEOVER)){setTitle("GameOver");}
		}
		
		JButton restart = new JButton("Restart");			
		JButton close = new JButton("Close");		
		
		restart.addActionListener(this);			
		close.addActionListener(this);
		
		j1.add(restart);			
		j1.add(close);
		//*******************************
		
		add(j.fileManager.getDisplayHighScore(), BorderLayout.NORTH);
		add(j1, BorderLayout.CENTER);
		pack();
		setResizable(false);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent a){
		String s = a.getActionCommand();				
		GameWindow g = (GameWindow) this.getOwner();	
		
		if(s.equals("Play")){						
			g.setGameState(GameState.PLAYING);
			g.setVisible(true);
			this.dispose();
		}else{
			if(g.fileManager.isNewHighScore(g.getCurrentPoint())){
				new NameDialog(g, s);
				g.setVisible(true);
				this.dispose();
				return;
			}
			
			if(s.equals("Restart")){
				g.restart();
				g.setVisible(true);
			} else {
				g.close();
			}
			this.dispose();
		}
		
		
	}
	
	private static class NameDialog extends JDialog implements ActionListener{
		public static final long serialVersionUID = 1L;
		private final JTextField name;
		private final String action;
		
		public NameDialog(GameWindow d, String action){
			super(d, "Novo HighScore");
			setLayout(new BorderLayout());
			
			JPanel j = new JPanel();
			j.setLayout(new FlowLayout());			
			
			JButton b = new JButton("OK");
			b.addActionListener(this);
			
			name = new JTextField(10);
			this.action = action;
			
			j.add(name);
			j.add(b);
			
			add(new JLabel("Introduz o teu Nome", JLabel.CENTER), BorderLayout.NORTH);
			add(j, BorderLayout.CENTER);
			
			
			pack();
			setResizable(false);
			setAlwaysOnTop(true);
			setLocationRelativeTo(null);
			setVisible(true);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
		
		public static boolean isNameValid(String s){
			if(s.isEmpty()){return false;}
			
			for (int i = 0; i < s.length(); i++) {      
			   char c = s.charAt(i);
			   if (	   (c >= 'a') && (c <= 'z') || 
					   (c >= 'A') && (c <= 'Z') ||
					   (c >= '0') && (c <= '9') ||
					   (c >= '�') && (c <= '�') ||
					   (c == ' ')
			   		) continue;
			   
			   return false;
			}  
			
			return true;
		}
		
		public void actionPerformed(ActionEvent a) {
			if(!isNameValid(name.getText())){return;}
			
			GameWindow g = (GameWindow) this.getOwner();
			g.fileManager.addNewScore(name.getText(), g.getCurrentPoint());
			
			if(action.equals("Restart")){
				g.restart();	
				this.dispose();
			}else{
				g.close();
				this.dispose();
			}			
			
			new TimedHighScore(g);
		}
	}

	private static class TimedHighScore extends JFrame {
		public static final long serialVersionUID = 1L;
		
		Timer timer;
		
		public TimedHighScore(GameWindow g){
			super("HighScore");
			timer = new Timer(5000, event -> {
				timer.stop();
				dispose();
			});
			
			add(g.fileManager.getDisplayHighScore());
			setSize(256, 128);
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			timer.start();
		}
	}
}