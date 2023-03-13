package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gamepanel extends JPanel implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 0, HEIGHT = 0;
	private Thread thread;
	private boolean running;
	private boolean right = true, left = false, up = false, down = false;
	private boolean right2 = true, left2 = false, up2 = false, down2 = false;
	
	private BodyPart b;
	private ArrayList<BodyPart> snake;
	
	private BodyPart b2;
	private ArrayList<BodyPart> snake2;
	private boolean multiplayer;
	
	private Apple apple;
	private ArrayList<Apple> apples;
	private Random appleRand;
	
	private Block block;
	private ArrayList<Block> blocks;
	private Random blockRand;
	
	private int xCoor = 10, yCoor = 10, size = 5;
	private int xCoor2 = 10, yCoor2 = 20;
	private int ticks = 0;
	
	
	private ArrayList<EvilBodyPart> CannonBall;
	private EvilBodyPart cannonBall;
	
	private JFrame f;
	private JLabel l;
	private ImageIcon i;
	private JPanel p;
	
	private ArrayList<Integer> Scores;
	
	
	
	//
	public Gamepanel() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("What size screen would you like to play on? (s, m, l)");
		while (WIDTH == 0) {
			String size = sc.next();
			if (size.equalsIgnoreCase("s"))
			{
				WIDTH = 300;
				HEIGHT = 300;
			} else if (size.equalsIgnoreCase("m"))
			{
				WIDTH = 500;
				HEIGHT = 500;
			} else if (size.equalsIgnoreCase("l"))
			{
				WIDTH = 750;
				HEIGHT = 750;
			} else {
			System.out.println("Invalid input, please enter either s, m, or l");
			}
		}
		
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		
		snake = new ArrayList<BodyPart>();
		
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
		
		apples = new ArrayList<Apple>();
		appleRand = new Random();
		
		blocks = new ArrayList<Block>();
		blockRand = new Random();
		
		
		Scores = new ArrayList<Integer>();
		
		CannonBall = new ArrayList<EvilBodyPart>();
		
		f = new JFrame("death image");
		i = new ImageIcon("C:/SpecTopics/Restart.png");
		l = new JLabel(i);
		p = new JPanel();
		p.add(l);
		f.add(p);
		f.setSize(WIDTH, HEIGHT);
		
		//start();
		
	}
	//.
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void restart() {
		running = false;
		System.out.println("You scored " + (size*100 - 500) + " points!");
		Scores.add(size*100 - 500);
		
		System.out.println("Hit right-arrow-key to continue\nHit left-arrow-key to see your statistics");
		
		
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
		f.setVisible(true);
	}
	
	public void stop() {
		
		//Exit
		
		/*running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		running = false;
		System.out.println("You scored " + (size*100 - 500) + " points!");
		Scores.add(size*100 - 500);
		
		int highscore = 100;
		for (int i = 0; i < Scores.size(); i++) {
			if (Scores.get(i) > highscore) {
				highscore = Scores.get(i);
			}
		}
		
		System.out.println("Snake is dead now");
		System.out.println("\nHere are some of your statistics:");
		if (highscore > 0) {
			System.out.println("Your high score was a measly " + highscore + " points...");
		} else if (highscore > 800) {
			System.out.println("Your high score was " + highscore + " points. Not bad");
		} else if (highscore > 1500) {
			System.out.println("Your high score was a whopping " + highscore + " points! You ate a whole lot of apples!");
		} else if (highscore > 2000) {
			System.out.println("You scored " + highscore + " points??? CONGRAGULATIONS");
		} else if (highscore > 3000) {
			//Display congragulations image (poyo!)
		}
		
		int totalScore = 0;
		for (int i = 0; i < Scores.size(); i++) {
			totalScore += Scores.get(i);
		}
		
		System.out.println("\nYour average score was " + (totalScore / Scores.size()) + " points.");
		
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
		
		double percentOfHS = (double) lowestScore/highscore * 100;
		
		if (percentOfHS == 1) {
			System.out.println("We hope your single playthrough was fun and exciting!");
		} else {
			System.out.println("Your lowest score was only " + percentOfHS + "% of your highest score!");
		}
	}
	
	//ffwa
	public void tick() {
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
		
		if (CannonBall.size() > 0 == true && ticks % 225000 == 0) {
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
		
		if (ticks % 650000 == 0) {
			if(right) xCoor++;
			if(left) xCoor--;
			if(up) yCoor--;
			if(down) yCoor++;
			if(right2) xCoor2++;
			if(left2) xCoor2--;
			if(up2) yCoor2--;
			if(down2) yCoor2++;
			
			
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
		
		if (snake2 != null) {
			if (ticks % 6500000 == 0) { //every 10 frames
				System.out.println("tenth tick");
				if (right2 == true) {
					cannonBall = new EvilBodyPart(xCoor2, yCoor2, 10, "right");
				} else if (left2 == true) {
					cannonBall = new EvilBodyPart(xCoor2, yCoor2, 10, "left");
				} else if (up2 == true) {
					cannonBall = new EvilBodyPart(xCoor2, yCoor2, 10, "up");
				} else {
					cannonBall = new EvilBodyPart(xCoor2, yCoor2, 10, "down");
				}
				CannonBall.add(cannonBall);
			}
		}
		
		if (blocks.size() > 15) //Max 15 blocks at a time
		{
			blocks.remove(0);
		}
		
		//Scrapped, ticks reset to 0 above, will leave for future use
		
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
		
		
		
		for (int i = 0; i < apples.size(); i++) {
			if (xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
				size++;
				apples.remove(i);
				i++;
			}
		}
		
		//blocks:
		
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
		
		
		for (int i = 0; i < snake.size(); i++) {
			if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if (i != snake.size() - 1) {
					System.out.println("Game Over (snake hit yourself)");
					restart();
				}
			}
		}
		
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
		
		if (xCoor < 0 || xCoor > WIDTH/10 - 1 || yCoor < 0 || yCoor > HEIGHT/10 - 1) {
			System.out.println("Game Over (snake out of bounds)");
			restart();
		}
		if (snake2 != null) { 
			if (xCoor2 < 0 || xCoor2 > WIDTH/10 - 1 || yCoor2 < 0 || yCoor2 > HEIGHT/10 - 1) {
				System.out.println("Game Over (snake2 out of bounds)");
				restart();
			}
		}
		
		if (snake2 != null) {
			for (int i = 0; i < snake.size(); i++) {
				if (xCoor2 == snake.get(i).getxCoor() && yCoor2 == snake.get(i).getyCoor())
				{
					System.out.println("Evil kirpy caught kirpy");
					restart();
				}
			}
		}
		
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
	//draw
	
	public void paint(Graphics g) {
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK); //background color
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		for (int i = 0; i < WIDTH/10; i++) //Draws lines going up (trying to make a grid) the 10 is by how much you want to increment the lines (spacing)
		{
			g.drawLine(i*10, 0, i*10, HEIGHT);
		}
		
		for (int i = 0; i < HEIGHT/10; i++) //Draws lines going sideways finishing the grid
		{
			g.drawLine(0, i*10, WIDTH, i*10);
		}
		
		for (int i = 0; i <snake.size(); i++)
		{
			snake.get(i).draw(g);
		}
		
		if (snake2 != null) {
			for (int i = 0; i < snake2.size(); i++) {
				snake2.get(i).draw(g);
			}
			for (int i = 0; i < CannonBall.size(); i++) {
				CannonBall.get(i).draw(g);
			}
		}
		
		
		for (int i = 0; i < apples.size(); i++) {
			apples.get(i).draw(g);
		} 
		
		for (int i = 0; i < blocks.size(); i++) {
			blocks.get(i).draw(g);
		}
		
		/*if (running == false) {
			myPrintMethod(g);
		}*/
	}
	

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
		// TODO Auto-generated method stub
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
			f.setVisible(false);
			start();
		}
		
		if (key == KeyEvent.VK_LEFT && running == false) {
			stop();
		}
		
		if (key == KeyEvent.VK_W && !down2) {
			up2 = true;
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
			down2 = true;
			left2 = false;
			right2 = false;
		}
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
// nothinggg

