package main;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.imageio.ImageIO;

public class Gamepanel extends JPanel implements Runnable, KeyListener {
	
	//Runnable variables
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean running;
	private int totalTickrate;
	
	//Direction Variables
	private boolean right = true, left = false, up = false, down = false;
	private boolean right2 = true, left2 = false, up2 = false, down2 = false;
	
	//Width height and coordinates
	public static int WIDTH = 0, HEIGHT = 0;
	private int xCoor = 10, yCoor = 10, size = 5;
	private int xCoor2 = 10, yCoor2 = 20;
	private int ticks = 0;
	
	//Snake Body parts
	private BodyPart b;
	private ArrayList<BodyPart> snake;
	private BodyPart b2;
	private ArrayList<BodyPart> snake2;
	
	//Apples + Blocks
	private Apple apple;
	private ArrayList<Apple> apples;
	private Random appleRand;
	private Block block;
	private ArrayList<Block> blocks;
	private Random blockRand;
	
	//Cannon ball
	private ArrayList<EvilBodyPart> CannonBall;
	private EvilBodyPart cannonBall;

	//Track of Scores
	private ArrayList<Integer> Scores;
	
	//Two player variables
	private int winThreshold;
	private boolean invulnerable;
	
	//Images
	private Image kirby;
	private Image deathImg;
	private Image TitleScreen;
	private Image blockImg;
	private Image appleImg;
	private Image congratsImg;
	
	
	
	public Gamepanel() {
		
		
		//Setting variables
		apples = new ArrayList<Apple>();
		appleRand = new Random();
		blocks = new ArrayList<Block>();
		blockRand = new Random();
		Scores = new ArrayList<Integer>();
		CannonBall = new ArrayList<EvilBodyPart>();
		snake = new ArrayList<BodyPart>();
		invulnerable = false;
		totalTickrate = 650000;
		
		
		//Screen Size question
		Scanner sc = new Scanner(System.in);
		System.out.println("What size screen would you like to play on? (s, m, l)");
		while (WIDTH == 0) {
			String size = sc.next();
			if (size.equalsIgnoreCase("s"))
			{
				WIDTH = 300;
				HEIGHT = 275;
			} else if (size.equalsIgnoreCase("m"))
			{
				WIDTH = 500;
				HEIGHT = 500;
			} else if (size.equalsIgnoreCase("l"))
			{
				WIDTH = 700;
				HEIGHT = 650;
			} else {
			System.out.println("Invalid input, please enter either s, m, or l");
			}
		}
		
		//Setting Gamepanel variables
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		
		//Setting Images
		try {
			kirby = ImageIO.read(getClass().getResourceAsStream("/resources/sprite_00.png"));
			deathImg = ImageIO.read(getClass().getResourceAsStream("/resources/adjusted death img.png"));
			TitleScreen = ImageIO.read(getClass().getResourceAsStream("/resources/kirby title screen.png"));
			blockImg = ImageIO.read(getClass().getResourceAsStream("/resources/newcrate.png"));
			appleImg = ImageIO.read(getClass().getResourceAsStream("/resources/tomato.png"));
			congratsImg = ImageIO.read(getClass().getResourceAsStream("/resources/tomato.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Single vs Player question
		System.out.println("Single player or double? (single, double)");
		String playerInput = sc.next();
		if (playerInput.equals("double")) {
			snake2 = new ArrayList<BodyPart>();
		} 
		while (!playerInput.equalsIgnoreCase("single") && !playerInput.equalsIgnoreCase("double")) {
			if (playerInput.equals("double")) {
				snake2 = new ArrayList<BodyPart>();
			} 
			System.out.println("Invalid input. Please enter either single or double");
			playerInput = sc.next();
		}
		
		
		//Double player question
		if (snake2 != null) {
			do {
				int num = 0;
				boolean inputOk = false;
			    do{
			    	System.out.println("What number of apples would you like to set for Kirpy to eat in order to beat Schmirpy? (Rec: 15)");
			        try {
			            num = sc.nextInt();
			            inputOk = true;
			        } catch (InputMismatchException  e) {
			            System.out.println("please enter a number (1, 2, 3)");
			            sc.nextLine(); // to reset the scanner
			        }
			    }while (!inputOk);
				
				if (num > 0 && num < 150) {
					winThreshold = num;
				} else {
					System.out.println("Invalid input, please select a number between 0 and 150");
				}
			} while(winThreshold == 0);
			totalTickrate = 450000;
		}
	}
	
	
	//Start game
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//Restart game
	public void restart() {
		
		running = false;
		System.out.println("You scored " + (size*100 - 500) + " points!");
		Scores.add(size*100 - 500);
		
		System.out.println("Hit right-arrow-key to continue\nHit left-arrow-key to see your statistics\nHit up-arrow-key to see your high score");
		
		
		this.xCoor = 10;
		this.yCoor = 10;
		this.size = 5;
		
		left = false;
		right = true;
		down = false;
		up = false;
		
		left2= false;
		right2 = true;
		down2 = false;
		up2 = false;
		
		snake.clear();
		apples.clear();
		blocks.clear();
		ticks = 0;
	}
	
	//Ends game
	public void stop() {
		running = false;
		System.out.println("You scored " + (size*100 - 500) + " points!");
		Scores.add(size*100 - 500);
		
		int highscore = 0;
		for (int i = 0; i < Scores.size(); i++) {
			if (Scores.get(i) > highscore) {
				highscore = Scores.get(i);
			}
		}
		
		//Result based on high score
		System.out.println("Snake is dead now");
		System.out.println("\nHere are some of your statistics:");
		if (highscore > 0) {
			System.out.println("Your high score was a measely " + highscore + " points...");
		} else if (highscore > 800) {
			System.out.println("Your high score was " + highscore + " points. Not bad");
		} else if (highscore > 1500) {
			System.out.println("Your high score was a whopping " + highscore + " points! You ate a whole lot of apples!");
		} else if (highscore > 2000) {
			System.out.println("You scored " + highscore + " points??? CONGRAGULATIONS");
		} else if (highscore > 3000) {
			//Display congragulations image (poyo!)
			//g.drawImage(congratsImg, 0, 0, null);
		}
		
		//Average Score
		int totalScore = 0;
		for (int i = 0; i < Scores.size(); i++) {
			totalScore += Scores.get(i);
		}
		System.out.println("\nYour average score was " + (totalScore / Scores.size()) + " points.\n");
		
		
		//Lowest Score
		int lowestScore = Scores.get(0);
		for (int i = 1; i < Scores.size(); i++) {
			if (Scores.get(i) < lowestScore) {
				lowestScore = Scores.get(i);
			}
		}
		if (lowestScore == 0) {
			System.out.println("Your lowest score was the lowest it goes, 0 points");
		} else {
			System.out.println("\nYour lowest score was " + lowestScore + " points.");
		}
		
		//Percentage of low score of high score
		double percentOfHS = (double) lowestScore/highscore * 100;
		if (percentOfHS == 1) {
			System.out.println("We hope your single playthrough was fun and exciting!");
		} else {
			System.out.println("Your lowest score was only " + percentOfHS + "% of your highest score!");
		}
	}
	
	//Double player End Game
	public void stop2() {
		
		running = false; 
		int wins = 0;
		for (int i = 0; i < Scores.size(); i++)
		{
			if (Scores.get(i) >= winThreshold*100)
			{
				wins++;
			}
		}
		System.out.println("Kirpy won " + wins + " times and lost " + (Scores.size() - wins) + " times!");
	}
	
	//Method runs every tick
	public void tick() {
		
		//Set up Snake
		if (snake.size() == 0)
		{
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
		}
		if (snake2 != null) {
			if (snake2.size() == 0) {
				b2 = new BodyPart(xCoor2, yCoor2, 10);
				snake2.add(b2);
			}
		}
		ticks++;
		
		//Movement
		if (ticks % totalTickrate == 0) {
			if(right) xCoor++;
			if(left) xCoor--;
			if(up) yCoor--;
			if(down) yCoor++;
			if(right2) xCoor2++;
			if(left2) xCoor2--;
			if(up2) yCoor2++;
			if(down2) yCoor2--;
			
			
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
			
			if (snake.size() > size) {
				snake.remove(0);
			}
			
			if (snake2 != null) {
				b2 = new BodyPart(xCoor2, yCoor2, 10);
				snake2.add(b2);
				
				if (snake2.size() > size) {
					snake2.remove(0);
				}
			}
			
			
			
		}
		
		//Shoots cannon ball every 10 frames
		if (snake2 != null) {
			if (ticks % (totalTickrate*10) == 0) {
				if (right2 == true) {
					cannonBall = new EvilBodyPart(xCoor2, yCoor2, 10, "right");
				} else if (left2 == true) {
					cannonBall = new EvilBodyPart(xCoor2, yCoor2, 10, "left");
				} else if (up2 == true) {
					cannonBall = new EvilBodyPart(xCoor2, yCoor2, 10, "up");
				} else if (down2 == true){
					cannonBall = new EvilBodyPart(xCoor2, yCoor2, 10, "down");
				}
				CannonBall.add(cannonBall);
			}
		}
		
		//Removes invulnerability after 10 frames
		if (invulnerable == true && ticks > totalTickrate*10) {
			invulnerable = false;
		}
		
		//Max of 15 blocks at a time
		if (blocks.size() > 15) 
		{
			blocks.remove(0);
		}
		
		//Creates apples and blocks
		if (apples.size() == 0) {
			int xCoor = appleRand.nextInt(WIDTH/10 - 1);
			int yCoor = appleRand.nextInt(HEIGHT/10 - 1);
			
			apple = new Apple(xCoor, yCoor, 10);
			apples.add(apple);
			
			int xCoorB = blockRand.nextInt(WIDTH/10 - 1);
			int yCoorB = blockRand.nextInt(HEIGHT/10 - 1);
				
			block = new Block(xCoorB, yCoorB, 10);
			blocks.add(block);
		}
		
		//Cannonball movement 
		if (CannonBall.size() > 0 == true && ticks % (totalTickrate*0.35) == 0) {
			for (int i = 0; i < CannonBall.size(); i++) {
				String direction = CannonBall.get(i).getDirection();
				switch (direction) {
				case "right":
					CannonBall.get(i).moveRight();
					break;
				case "left":
					CannonBall.get(i).moveLeft();
					break;
				case "up":
					CannonBall.get(i).moveUp();
					break;
				case "down":
					CannonBall.get(i).moveDown();
					break;
				}
			}
		}
		
		
		//Eating apples
		for (int i = 0; i < apples.size(); i++) {
			if (xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
				size++;
				apples.remove(i);
				i++;
				
				if (snake2 != null) {
					invulnerable = true;
					ticks = 0;
				}
			}
		}
		
		//DEATHS:
		
		
		//By Block
		for (int i = 0; i < blocks.size(); i++)
		{
			if (xCoor == blocks.get(i).getxCoor() && yCoor == blocks.get(i).getyCoor())
			{
				System.out.println("Game Over (snake hit wall)");
				restart();
			}
		}
		
		
		if (snake2 != null) {
			for (int i = 0; i < blocks.size(); i++)
			{
				if (xCoor2 == blocks.get(i).getxCoor() && yCoor2 == blocks.get(i).getyCoor())
				{
					System.out.println("Game Over (snake2 hit wall)");
					restart();
				}
			}
		}
		
		//By running into yourself
		for (int i = 0; i < snake.size(); i++) {
			if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if (i != snake.size() - 1) {
					System.out.println("Game Over (snake hit yourself)");
					restart();
				}
			}
		}
		
		//By running out of bounds
		if (xCoor < 0 || xCoor > WIDTH/10 - 1 || yCoor < 0 || yCoor > HEIGHT/10 - 1) {
			System.out.println("Game Over (snake out of bounds)");
			restart();
		}
		
		
		
		//Double player deaths:
		
		
		//Schmirpy runs into himself
		if (snake2 != null) {
			for (int i = 0; i < snake2.size(); i++) {
				if (xCoor2 == snake2.get(i).getxCoor() && yCoor2 == snake2.get(i).getyCoor()) {
					if (i != snake2.size() - 1) {
						System.out.println("Game Over (player 2 hit themself)");
						restart();
					}
				}
			}
		}
		
		//Schmirpy runs out of bounds
		if (snake2 != null) { 
			if (xCoor2 < 0 || xCoor2 > WIDTH/10 - 1 || yCoor2 < 0 || yCoor2 > HEIGHT/10 - 1) {
				System.out.println("Game Over (snake2 out of bounds)");
				restart();
			}
		}
		
		//Checks invulnerability
		if (invulnerable == false)
		{
			//Getting caught by Schmirpy 
		if (snake2 != null) {
			for (int i = 0; i < snake.size(); i++) {
				if (xCoor2 == snake.get(i).getxCoor() && yCoor2 == snake.get(i).getyCoor())
				{
					System.out.println("Evil kirpy caught kirpy");
					restart();
				}
			}
		}
		
		//Cannonball kills Kirpy
		if (snake2 != null) {
			for (int i = 0; i < CannonBall.size(); i++) {
				int x = CannonBall.get(i).getxCoor();
				int y = CannonBall.get(i).getyCoor();
				
				for (int j = 0; j < snake.size(); j++) {
					if (snake.get(i).getxCoor() == x && snake.get(i).getyCoor() == y) {
						System.out.println("Game over, schmirpy shot and killed kirpy");
						restart();
					}
				}
			}
		}
	}
	}
	
	//Drawing method
	public void paint(Graphics g) {
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		//Background for multiplayer vs singleplayer
		if (snake2!=null) {
			g.setColor(Color.DARK_GRAY);
		} else {
		g.setColor(Color.BLACK); //background color
		}
		
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		if (snake2!=null) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.BLUE);	
		}
		
		//Draws lines going up (trying to make a grid) the 10 is by how much you want to increment the lines (spacing)
		for (int i = 0; i < WIDTH/10; i++) 
		{
			g.drawLine(i*10, 0, i*10, HEIGHT);
		}
		
		//Draws lines going sideways finishing the grid
		for (int i = 0; i < HEIGHT/10; i++) 
		{
			g.drawLine(0, i*10, WIDTH, i*10);
		}
		
		//Draws Kirby
		for (int i = 0; i <snake.size(); i++)
		{
			snake.get(i).draw(g, kirby);
		}
		
		//Draws Schmirpy 
		if (snake2 != null) {
			for (int i = 0; i < snake2.size(); i++) {
				snake2.get(i).drawEvil(g);
			}
			//Draws Cannonballs
			for (int i = 0; i < CannonBall.size(); i++) {
				CannonBall.get(i).draw(g);
			}
		}
		
		//Draws apples
		for (int i = 0; i < apples.size(); i++) {
			apples.get(i).drawImg(g, appleImg);
		} 
		
		//Draws Blocks
		for (int i = 0; i < blocks.size(); i++) {
			blocks.get(i).drawImg(g, blockImg);
		}
		
		//Draws title screen
		if (Scores.size() == 0 && running == false)
		{
			g.clearRect(0, 0, WIDTH, HEIGHT);
			g.drawImage(TitleScreen, 0, 0, null);
		}
		
		//Draws death screen
		if (Scores.size() > 0 && running == false)
		{
			g.clearRect(0, 0, WIDTH, HEIGHT);
			g.drawImage(deathImg, 0, 0, null);
		}
	}
	

	//Run method
	public void run() {
		
		while (running)
		{
			tick();
			repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	
	//movement, takes input and changes direction
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT && !left) {
			right = true;
			up = false;
			down = false;
		}
		
		if (key == KeyEvent.VK_LEFT && !right) {
			left = true;
			up = false;
			down = false;
		}
		
		if (key==KeyEvent.VK_UP && !down) {
			up = true;
			left = false;
			right = false;
		}
		
		if(key == KeyEvent.VK_DOWN && !up) {
			down = true;
			left = false;
			right = false;
		}
		
		if (key == KeyEvent.VK_RIGHT && running == false) {
			right = true;
			up = false;
			down = false;
			start();
		}
		
		if (key == KeyEvent.VK_UP && running == false) {
			int highscore = 0;
			for (int i = 0; i < Scores.size(); i++) {
				if (Scores.get(i) > highscore) {
					highscore = Scores.get(i);
				}
			}
			System.out.println("Your high score is " + highscore + " points!");
		}
		
		if (key == KeyEvent.VK_LEFT && running == false && snake2 == null) {
			stop();
		}
		
		if (key == KeyEvent.VK_LEFT && running == false && snake2 != null) {
			stop2();
		}
		
		
		if (key == KeyEvent.VK_W && !down2) {
			down2 = true;
			right2 = false;
			left2 = false;
		}
		
		if (key == KeyEvent.VK_A && !right2) {
			left2 = true;
			up2 = false;
			down2 = false;
		}
		
		if (key == KeyEvent.VK_D && !left2) {
			right2 = true;
			up2 = false;
			down2 = false;
		}
		
		if (key == KeyEvent.VK_S && !up2) {
			up2 = true;
			left2 = false;
			right2 = false;
		}
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
}


