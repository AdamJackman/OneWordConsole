package oneWord;

import java.util.Scanner;

public class oneWord {
	//Initial setup
	public static UserDAO udao = new UserInDatabase();
	public static HashDAO hdao = new HashInTest();
	public static Scanner uin = new Scanner(System.in);
	public static String username, password;
	public static boolean logged;
	public static User me;
	
	//The main method will be ran here
	public static void main(String args[]){
		
		logged = false;
		//TODO: Create some sort of GUI
		//boolean logged = false;
		
		System.out.println("----- ONE WORD -----");
		while (true){
			if(!logged){				
				/**
				 * Possible commands: 
				 * 	Log in: Just logs in 
				 * 	Register: Adds the new user and then logs in 
				 * 	Exit: Quits the program
				 */
				//
				System.out.println("Please enter your command");
				System.out.println("(login, register, exit)");
				String command = uin.nextLine();
				//TODO: Strip the command, case insensitive that sort of thing
				
				//Implement the functionality user requests
				if (command.equals("login")){ 
					logIn(); 
				}
				else if (command.equals("register")){ 
					register(); 
				}
				else if (command.equals("exit")){
					System.out.println("Thank you for using OneWord");
					uin.close(); 
					break; 
				}
				else { 
					System.out.println("Unrecognised command"); 
				}
			}
			else{
				//The User is now logged in with data in the me variable
				System.out.println("----- ONE WORD ----- USER: " + me.getUsername().toUpperCase() + " -----");
				
				/**
				 * Possible Commands:
				 * access:
				 * log out: 
				 * exit: 
				 */
				System.out.println("Please enter your command");
				System.out.println("(access, logout, exit)");
				String command = uin.nextLine();
				command = command.trim();
				//TODO: Strip the command, case insensitive that sort of thing
				if (command.equals("access")){
					System.out.println("accessing");
					access();
				}
				else if (command.equals("logout")){
					System.out.println("logging out");
					//Remove information
					// TODO: Consider calling a function to clear the console
					username = "";
					password = "";
					me = null;
					logged = false;
				}
				else if (command.equals("exit")){
					System.out.println("Thank you for using OneWord");
					uin.close();
					break;
				}
				else { 
					System.out.println("Unrecognised command"); 
				}
			}
		}

		
	}
	
	//Used Functions
	public static void logIn(){
		//Gather the user information
		System.out.println("Enter Username");
		username = uin.nextLine();				
		
		//Attempt user log in
		me = udao.login(username);
		if (me == null){
			//The Log in was unsuccessful - do not change the logged variable
			System.out.println("Log in unsuccessful - user does not exist");
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
	
		//Attempt user register
		me = udao.registerUser(username);
		if (me == null){
			//Register unsuccessful - do not change the logged variable
			System.out.println("Username already exists - please retry");
		}
		else{
			System.out.println("Welcome to oneWord - You are now registered and logged in");
			logged = true;
		}
	}

	/*
	 * This Function is used to print out the user's hashed password onto the screen 
	 * it will get all the needed information from the HashDAO.
	 */
	public static void access(){
		//going to need to place this in there somewhere
		//System.out.println("Enter Password");
		//password = uin.nextLine();
		
		System.out.println("Enter the site name");
		String sitename = uin.nextLine();
	
		//Check whether the site currently has a hash
		boolean exists = hdao.isHash(me, sitename);
		if(!exists){
			System.out.println("You have not used this site before, Would you like to create a new access password? (yes, no)");
			String response = uin.nextLine();
			//If the user wants to create the new hash
			if(response.equals("yes")){
				//proceed with the access call
				hdao.makeHash(me, sitename);
				String hash = hdao.getHash(me, sitename);
				System.out.println("Enter your word");
				password = uin.nextLine();
				//TODO: is it possible to hide the input characters
				
				String hashed = hdao.getPassword(me, sitename, hash, password);
				//Some spaces to make things clearer
				System.out.println("\n\n Your Password is: " + hashed + "\n");
				//sanitize just in case
				hash = "";
				hashed = "";
			}
			else{
				return;
			}
		}
		else{
			//There is already a hash so simply get it
			String hash = hdao.getHash(me, sitename);
			System.out.println("Enter your word");
			password = uin.nextLine();
			//Some spaces to make things clearer
			String hashed = hdao.getPassword(me, sitename, hash, password);
			System.out.println("\n\n Your Password is: " + hashed + "\n");
			//sanitize just in case
			hash = "";
			hashed = "";
		}		
	}
}
