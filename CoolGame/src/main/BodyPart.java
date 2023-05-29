package main;
import java.awt.*;
import java.awt.image.ImageObserver;

public class BodyPart implements ImageObserver{
	
	private int xCoor, yCoor, tileSize;
	
	public BodyPart(int xCoor, int yCoor, int tileSize) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.tileSize = tileSize;
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.PINK);
		g.fillRect(xCoor * tileSize, yCoor* tileSize, tileSize, tileSize);
	}
	
	public void draw(Graphics g, Image img) {
		g.drawImage(img, xCoor * tileSize-10, yCoor * tileSize-15, this);
	}
	
	public void drawEvil(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(xCoor * tileSize, yCoor* tileSize, tileSize, tileSize);
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

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
