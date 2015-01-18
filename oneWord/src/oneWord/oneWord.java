package oneWord;

import java.util.Scanner;

public class oneWord {
	//Initial setup
	public static UserDAO udao = new UserInTest();
	public static HashDAO hdao = new HashInTest();
	public static Scanner uin = new Scanner(System.in);
	public static String username, password;
	public static boolean logged;
	
	//The main method will be ran here
	public static void main(String args[]){
		
		logged = false;
		//TODO: Create some sort of GUI
		//boolean logged = false;
		
		System.out.println("Enter the program");
		while (true){
			if(!logged){
				
				//TODO: Present the user the choice to Log in or Register, or exit I guess.
				/**
				 * Possible commands: 
				 * Log in: 
				 * Register: 
				 * Exit: 
				 */
				System.out.println("Not Logged In While");
				//logIn();
				register();

				
			}
			else{
				//The User is now logged in with data in the me variable
				System.out.println("Logged In While");
				
				/**
				 * Possible Commands:
				 * getForSite (X):
				 * log out: 
				 * exit: 
				 */
				
				//Artificial break - TODO: this will become exit
				uin.close();
				break;
			}
		}

		
	}
	
	//Used Functions
	
	public static void logIn(){
		//Gather the user information
		System.out.println("Enter Username");
		username = uin.nextLine();
		System.out.println("Enter Password");
		password = uin.nextLine();				
		
		//Attempt user log in
		//TODO: Possibly encrypt the password before it is sent to the db
		User me = udao.login(username, password);
		if (me == null){
			//The Log in was unsuccessful - do not change the logged variable
			System.out.println("Log in unsuccessful");
			//TODO: Add security to watch for multiple attempts
		}
		else{
			//The User is now logged in and the data is stored in the me variable
			logged = true;
		}
	}
	
	public static void register(){
		//Gather the user information
		System.out.println("Enter Username");
		username = uin.nextLine();
		System.out.println("Enter Password");
		password = uin.nextLine();	
		
		//Attempt user register
		//TODO: Security checks on passwords, undecided
		User me = udao.registerUser(username, password);
		if (me == null){
			//Register unsuccessful - do not change the logged variable
			System.out.println("Username already exists - please retry");
		}
		else{
			System.out.println("Welcome to oneWord - You are now registered and logged in");
			logged = true;
		}
	}

	
}
