package settings;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import elements.Block;
import elements.Cell;

public class FileManager {
	private String highScorePath;
	private String labyrinthPath;
	
	private Player[] scores;
	
	public static final String HIGHSCORE_PATH = "files\\high scores\\";
	public static final String HIGHSCORE_EXT = ".high";
	public static final String LABYRINTH_PATH = "files\\labyrinths\\";
	public static final String LABYRINTH_EXT = ".labyrinth";
	public static final String NOLABYRINTH = "noLabyrinth";
	
		
	public FileManager(String labyrinthName){		
		if(labyrinthName == null){
			highScorePath = HIGHSCORE_PATH + NOLABYRINTH + HIGHSCORE_EXT;
			labyrinthPath = null;
		}
		else{
			highScorePath = HIGHSCORE_PATH + labyrinthName + HIGHSCORE_EXT;
			labyrinthPath = LABYRINTH_PATH + labyrinthName + LABYRINTH_EXT;
		}
		
		scores = new Player[5];
		
		loadHighScores();
	}
	
	public boolean isNewHighScore(int points){
		if(points <= 0){return false;}		
		
		for( int i = 0; i < scores.length; i++){
			if(scores[i] == null){return true;}
			if(scores[i].getScore() < points){return true;}
		}
		
		return false;
	}
	
	public void addNewScore(String name, int points){		
		int lastIdx = scores.length - 1;
		for( int i = 0; i < scores.length; i++){
			if(scores[i] == null){lastIdx = i; break;}
		}
		
		for( int i = 0; i <= lastIdx; i++){
			if(scores[i] == null){scores[i] = new Player(name, points); break;}
			
			if(scores[i].getScore() < points){				
				for(int i2 = lastIdx; i2 > i; i2--){
					scores[i2] = scores[i2 - 1];  
				}
				scores[i] = new Player(name, points);
				break;
			}
		}
		
		saveHighScores();
	}
	
	private void saveHighScores(){
		PrintWriter pw;		
		
		try{			
		pw = new PrintWriter(highScorePath);
				
		for(int i = 0; i < scores.length; i++){
			if(scores[i] == null){break;}
			pw.println(scores[i]);
		}
		
		pw.close();
		
		}catch(IOException e){System.exit(0);}		
	}	
	
	private void loadHighScores(){
		BufferedReader br;
		
		try{
		br= new BufferedReader(new FileReader(highScorePath));		
				
		String s;
		int idx = 0;
		scores = new Player[5];
		
		while((s = br.readLine()) != null){
			scores[idx++] = new Player(
					(s.split(Player.SEPARATOR))[0],
					Integer.parseInt((s.split(" - "))[1]) );
		}
		
		br.close();		
		}catch(FileNotFoundException e){
			File f = new File(highScorePath);
			try{f.createNewFile();}catch(IOException ioe){}
		}
		catch(IOException e){System.exit(0);}
	}
	
	public void loadLabyrinth(Cell[][] c){
		if(labyrinthPath != null){loadLabyrinth(c, labyrinthPath);}		
	}
	
	public static void loadLabyrinth(Cell[][] c, String labyrinthPath){
		BufferedReader br;
		
		try{		
		br = new BufferedReader(new FileReader(labyrinthPath));

		String s;
		String[] xy;
		
		Block b = new Block();

		while((s = br.readLine()) != null){
			xy = s.split(",");
			
			int x = Integer.parseInt( xy[0] );
			int y = Integer.parseInt( xy[1] );
			
			c[x][y].setFigure(b);			
		}
		
		br.close();
		}catch(IOException e){System.exit(0);;}
	}
	
	public JPanel getDisplayHighScore(){
		JPanel d = new JPanel();		
		d.setLayout(new GridLayout(6, 1));
		
		String s = "";
		
		if(labyrinthPath != null){
			char[] aux = labyrinthPath.toCharArray();
		
			for(int i = labyrinthPath.lastIndexOf(".") - 1; i >= 0; i--){
				if( aux[i] == '\\' ) break;
				s = aux[i] + s;
			}
		}else{
			s = "No Labyrinth";
		}
		
		JLabel l = new JLabel( s, JLabel.CENTER);
		l.setFont(new Font("Comic Sans MS", Font.BOLD, 14));		
		d.add(l);
		
		for( int i = 0; i < scores.length; i++){
			if(scores[i] == null){
				d.add(new JLabel((i + 1) + "º -  ------------------", JLabel.CENTER));
			}else{
				d.add(new JLabel((i + 1) + "º - " +scores[i].toString(), JLabel.CENTER));
			}
		}
		
		return d;
	}
}