package main;

import java.awt.Color;
import java.awt.Graphics;

public class EvilBodyPart {
	
	private int xCoor, yCoor, width, height;
	private String direction;
	
	public EvilBodyPart(int xCoor, int yCoor, int tileSize, String direction) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		width = tileSize;
		height = tileSize;
		this.direction = direction;
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(xCoor * width, yCoor* height, width, height);
	}
	
	public int getxCoor() {
		return xCoor;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}
	
	public int getyCoor() {
		return yCoor;
	}
	
	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}
	
	public void setDirection(String dir) {
		direction = dir;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void moveRight() {
		xCoor++;
	}
	
	public void moveLeft() {
		xCoor--;
	}
	
	public void moveUp() {
		yCoor++;
	}
	
	public void moveDown() {
		yCoor--;
	}
}
