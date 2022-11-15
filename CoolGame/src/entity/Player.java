package entity;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Gamepanel;

public class Player extends Entity{
	
	Gamepanel gp;
	
	public Player(Gamepanel gp) {
		
		this.gp = gp;
		setDefaultValues();
		getPlayerImage(); 
		
	}

	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 1000;
	}
	
	public void getPlayerImage() {
		try {
			
		one = ImageIO.read(getClass().getResourceAsStream("/player/sprite_00.png")); 
		two = ImageIO.read(getClass().getResourceAsStream("/player/sprite_01.png")); 
		three = ImageIO.read(getClass().getResourceAsStream("/player/sprite_02.png")); 
		four = ImageIO.read(getClass().getResourceAsStream("/player/sprite_03.png")); 
		five = ImageIO.read(getClass().getResourceAsStream("/player/sprite_04.png")); 
		six= ImageIO.read(getClass().getResourceAsStream("/player/sprite_05.png")); 
		seven = ImageIO.read(getClass().getResourceAsStream("/player/sprite_06.png")); 
		eight = ImageIO.read(getClass().getResourceAsStream("/player/sprite_07.png")); 
		nine = ImageIO.read(getClass().getResourceAsStream("/player/sprite_08.png")); 
		ten = ImageIO.read(getClass().getResourceAsStream("/player/sprite_09.png")); 
		eleven = ImageIO.read(getClass().getResourceAsStream("/player/sprite_10.png")); 
		twelve = ImageIO.read(getClass().getResourceAsStream("/player/sprite_11.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
