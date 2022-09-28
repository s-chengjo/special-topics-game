import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
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
		
		System.out.println("Get ready " + name + ", you are about to begin (name of game) on " + difficulty + " level!");
		//balh
		//blah
	}

}
