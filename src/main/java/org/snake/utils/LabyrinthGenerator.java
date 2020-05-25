package org.snake.utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class LabyrinthGenerator {
	PrintWriter pw;
	
	public LabyrinthGenerator(String pathOut) throws FileNotFoundException, IOException 
	{pw = new PrintWriter(pathOut);}
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		LabyrinthGenerator l = new LabyrinthGenerator("D:\\ISEL\\2� Semestre\\POO\\Trabalho 3\\Snake\\files\\labyrinths\\n4.labyrinth");
		l.drawLine(10, 10, 10, 20);
		l.drawLine(20, 10, 20, 20);		
		l.close();
	}
	
	public void close(){pw.close();}
	
	public void drawLine(int x1, int y1, int x2, int y2) throws FileNotFoundException, IOException{
		while(x1 != x2 || y1 != y2){
			pw.println(x1 + "," + y1);
			
			if(x1 < x2){x1++;}
			else{if(x1 > x2){x1--;}}
			
			if(y1 < y2){y1++;}
			else{if(y1 > y2){y1--;}}
		}		
	}	
}
