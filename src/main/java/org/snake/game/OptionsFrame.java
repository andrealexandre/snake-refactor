package org.snake.game;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;

import org.snake.settings.FileManager;
import org.snake.settings.GameConfiguration;

public class OptionsFrame extends JFrame implements ActionListener{
	public static final long serialVersionUID = 1L;
	
	private GameMenu caller;	
	private LabyrinthPreviewer lp;
	
	private JRadioButton snakeSpeedEasy;
	private JRadioButton snakeSpeedMedium;
	private JRadioButton snakeSpeedHard;

	public OptionsFrame(GameMenu caller){
		super("Options");
		this.setResizable(false);		
		
		this.caller = caller;
		this.caller.setVisible(false);
		
		setLayout(new BorderLayout());
		
		//******Choosing Buttons***********
		JButton ok = new JButton("OK");
		JButton cancel = new JButton("Cancel");		
		
		JPanel buttons = new JPanel();				
		buttons.add(ok);
		buttons.add(cancel);		
								
		add(buttons, BorderLayout.SOUTH);				
		
		ok.addActionListener(this);
		cancel.addActionListener(this);			
		
		//*******RadioButtons*****
		snakeSpeedEasy = new JRadioButton("Easy");
		snakeSpeedMedium = new JRadioButton("Medium", true);
		snakeSpeedHard = new JRadioButton("Hard");		
		
		ButtonGroup group = new ButtonGroup();				
		group.add(snakeSpeedEasy);
		group.add(snakeSpeedMedium);
		group.add(snakeSpeedHard);
		
		JPanel radio = new JPanel();
		radio.setLayout(new FlowLayout());
		radio.add(snakeSpeedEasy);
		radio.add(snakeSpeedMedium);
		radio.add(snakeSpeedHard);
		
		JPanel j = new JPanel();
		j.setLayout(new BorderLayout());
		j.setBorder(new EtchedBorder());
		j.add(new JLabel("Snake Speed", JLabel.CENTER), BorderLayout.NORTH);
		j.add(radio, BorderLayout.CENTER);
				
		add(j, BorderLayout.NORTH);
		
		//**LabyrintPreviewer**
		lp = new LabyrinthPreviewer();
		add(lp, BorderLayout.CENTER);		
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void creatGameConfiguration(){
		GameConfiguration g = caller.config;
		
		if(snakeSpeedEasy.isSelected()){
			g.setSnakeSpeedAtEasy();			
		}else{
		if(snakeSpeedMedium.isSelected()){
			g.setSnakeSpeedAtMedium();			
		}else{
		if(snakeSpeedHard.isSelected()){
			g.setSnakeSpeedAtHard();			
		}
		}
		}
		
		g.setLabyrinth(lp.getLabyrinthName());
		
		caller.config = g;					
	}
	
	public void actionPerformed(ActionEvent a){
		String s = a.getActionCommand();			

		if(s.equals("OK")){
			creatGameConfiguration();
			this.dispose();
			caller.setVisible(true);					
		}else{
			if(s.equals("Cancel")){
				this.dispose();
				caller.setVisible(true);
		}
		}
	}

	private class LabyrinthPreviewer extends JPanel implements ActionListener{
		public static final long serialVersionUID = 1L;
			Board b;
			JLabel labyrinthName;
			String[] labyrinths;
			int idx;
		
		public LabyrinthPreviewer(){
			super();
			setLayout(new BorderLayout());
			setBorder(new EtchedBorder());
			
			JButton next = new JButton("Next");			
			JButton previous = new JButton("Previous");
			
			next.addActionListener(this);			
			previous.addActionListener(this);
			
			labyrinthName = new JLabel("NoLabyrinth", JLabel.CENTER);
			labyrinthName.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
			
			add(next, BorderLayout.EAST);
			add(labyrinthName, BorderLayout.CENTER);
			add(previous, BorderLayout.WEST);
						
			b = new Board();
			add(b , BorderLayout.NORTH);
			
			File f = new File("src/main/java/org/snake/files/labyrinths/");
			labyrinths = f.list();
			
			for(int i = 0; i < labyrinths.length; i++){				
				labyrinths[i] = labyrinths[i].substring(0, labyrinths[i].lastIndexOf("."));				
			}
			
			idx = -1;
		}
		
		public String getLabyrinthName(){
			if( idx < 0 || idx >= labyrinths.length ){return null;}
			
			return labyrinths[idx];			
		}
		
		public void actionPerformed(ActionEvent a){
			String action = a.getActionCommand();
			b.clearLabyrinth();
			
			if(action.equals("Next")){
				++idx;
				if(idx >= labyrinths.length){
					idx = -1;
					labyrinthName.setText("No Labyrinth");
				}else{
					String LabyrinthPath = FileManager.LABYRINTH_PATH + labyrinths[idx] + FileManager.LABYRINTH_EXT;
					FileManager.loadLabyrinth(b.getBoard(), LabyrinthPath);
					labyrinthName.setText(labyrinths[idx]);
				}
			}else{				
				if(action.equals("Previous")){
					--idx;
					if(idx < 0){
						idx = labyrinths.length;
						labyrinthName.setText("No Labyrinth");
					}
					else{
						String LabyrinthPath = FileManager.LABYRINTH_PATH + labyrinths[idx] + FileManager.LABYRINTH_EXT;
						FileManager.loadLabyrinth(b.getBoard(), LabyrinthPath);
						labyrinthName.setText(labyrinths[idx]);
					}					
				}
			}
		}
	}
}