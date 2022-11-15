package main;
import java.util.*;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		/*Scanner sc = new Scanner(System.in);
		System.out.println("Welcome player");
		System.out.println("Please enter your name (20 character limit): ");
		String name = sc.nextLine();
		
		while (name.length() > 20)
		{
			System.out.println("The name you entered is too long.");
			System.out.println("Please reenter your name (20 character limit): ");
			name = sc.nextLine();
		}
		System.out.println("Hello, " + name + ", welcome to (name of game)");
		
		System.out.println("\nWhat difficulty would you like to play? (Enter easy, medium, or hard)");
		String difficulty = sc.nextLine().toLowerCase();
		while (!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard") && !difficulty.equals("Insane")) { //"Insane" will be an easter egg
			System.out.println("Invalid difficulty input");
			System.out.println("Please reenter what difficulty you would like to play (easy, medium, or hard).");
			difficulty = sc.nextLine().toLowerCase();
		}
		
		System.out.println("Get ready " + name + ", you are about to begin (name of game) on " + difficulty + " level!"); */
		
		
		JFrame frame = new JFrame();
		Gamepanel gamepanel = new Gamepanel();
		
		frame.add(gamepanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Snake Game");
		
		frame.pack(); //Sets window size based on preferred sizes
		frame.setVisible(true); //shows window
		frame.setLocationRelativeTo(null); //Choose where the window opens, null means in the center of screen, default is at top left corner
		
		
	}

}
