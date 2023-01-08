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
	public static int WIDTH = 500, HEIGHT = 500;
	private Thread thread;
	private boolean running;
	private boolean right = true, left = false, up = false, down = false;
	
	private BodyPart b;
	private ArrayList<BodyPart> snake;
	
	private Apple apple;
	private ArrayList<Apple> apples;
	private Random appleRand;
	
	private Block block;
	private ArrayList<Block> blocks;
	private Random blockRand;
	
	private int xCoor = 10, yCoor = 10, size = 5;
	private int ticks = 0;
	
	private JFrame f;
	private JLabel l;
	private ImageIcon i;
	private JPanel p;
	
	private ArrayList<Integer> Scores;
	
	
	
	
	public Gamepanel() {
		
		/*Scanner sc = new Scanner(System.in);
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
			}
		}
		*/
		
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		
		snake = new ArrayList<BodyPart>();
		apples = new ArrayList<Apple>();
		appleRand = new Random();
		
		blocks = new ArrayList<Block>();
		blockRand = new Random();
		
		
		Scores = new ArrayList<Integer>();
		
		f = new JFrame("label");
		i = new ImageIcon("C:/SpecTopics/Restart.png");
		l = new JLabel(i);
		p = new JPanel();
		p.add(l);
		f.add(p);
		f.setSize(WIDTH, HEIGHT);
		
		
		start();
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
		
		
		this.xCoor = 10;
		this.yCoor = 10;
		this.size = 5;
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
		
		int highscore = 0;
		for (int i = 0; i < Scores.size(); i++) {
			if (Scores.get(i) > highscore) {
				highscore = Scores.get(i);
			}
		}
		
		System.out.println("GG player!");
		System.out.println("\nHere are some of your statistics:");
		if (highscore > 400) {
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
		
		System.out.println("\nYour lowest score was " + lowestScore + " points.");
		
		double percentOfHS = (double) lowestScore/highscore * 100;
		
		if (percentOfHS == 1) {
			System.out.println("We hope your single playthrough was fun and exciting!");
		} else {
			System.out.println("Your lowest score was only " + percentOfHS + "% of your highest score!");
		}
	}
	
	
	public void tick() {
		if (snake.size() == 0)
		{
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
		}
		ticks++;
		
		if (ticks > 650000) {
			if(right) xCoor++;
			if(left) xCoor--;
			if(up) yCoor--;
			if(down) yCoor++;
			
			ticks = 0;
			
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
			
			if (snake.size() > size) {
				snake.remove(0);
			}
		}
		
		if (ticks > 6500000) //every 10 ticks
		{
			blocks.remove(blocks.size()-1);
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
				System.out.println("Game Over (hit wall)");
				restart();
			}
		}
		
		for (int i = 0; i < snake.size(); i++) {
			if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if (i != snake.size() - 1) {
					System.out.println("Game Over (hit yourself)");
					restart();
				}
			}
		}
		
		if (xCoor < 0 || xCoor > WIDTH/10 - 1 || yCoor < 0 || yCoor > HEIGHT/10 - 1) {
			System.out.println("Game Over (out of bounds)");
			restart();
		}
	}
	
	//dra
	
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
		
		for (int i = 0; i < apples.size(); i++) {
			apples.get(i).draw(g);
		}
		
		for (int i = 0; i < blocks.size(); i++) {
			blocks.get(i).draw(g);
		}
		
		if (running == false) {
			myPrintMethod(g);
		}
	}
	
	public static void myPrintMethod(Graphics g){
	    g.drawString("Game Over, hit right-arrow-key to play again!",WIDTH/2,HEIGHT/2); 
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
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
// nothi
