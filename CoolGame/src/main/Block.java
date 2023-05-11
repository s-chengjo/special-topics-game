package main;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Block implements ImageObserver{
	
	private int xCoor, yCoor, width, height;
	
	public Block(int xCoor, int yCoor, int tileSize) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		width = tileSize;
		height = tileSize;
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(xCoor * width, yCoor* height, width, height);
	}
	
	public void drawImg(Graphics g, Image img) {
		g.setColor(Color.MAGENTA);
		g.drawImage(img, xCoor * width, yCoor * height, this);
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
