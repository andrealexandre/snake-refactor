package settings;

public class Player{
	private String name;
	private int score;
	
	public static final String SEPARATOR = " - ";
	
	public Player(String name, int score){
		this.name = name;
		this.score = score;
	}
	
	public int getScore(){return score;}
	
	public String toString(){return name + SEPARATOR + score;} 	
}