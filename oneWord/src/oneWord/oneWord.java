package oneWord;

import java.util.Scanner;

public class oneWord {
	//Initial setup
	public static UserDAO udao = new UserInDatabase();
	//public static HashDAO hdao = new HashInTest();
	public static HashDAO hdao = new HashInDatabase();
	public static Scanner uin = new Scanner(System.in);
	public static String [] bannedList= {";", "\'","-", "\\"};
	public static String username, password;
	public static boolean logged;
	public static User me;
	
	//The main method will be ran here
	public static void main(String args[]){
		
		logged = false;
		//TODO: Create some sort of GUI
		
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
				//Strip the command of case insensitive and whitespace
				command = command.toLowerCase();
				command = command.replaceAll("\\s","");

				
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
				 * Access: Will use oneword and sitename to create the password hash
				 * Log out: simply logs out
				 * Exit: exits the while loop
				 */
				System.out.println("Please enter your command");
				System.out.println("(access, logout, exit)");
				String command = uin.nextLine();
				// sanitize input
				command = command.trim();
				command = command.toLowerCase();
				command = command.replaceAll("\\s","");
				
				if (command.equals("access")){
					access();
				}
				else if (command.equals("logout")){
					System.out.println("Successfully Logged Out");
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
		}//END WHILE
	}//END MAIN
	
	//Start Functions
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
		System.out.println("Enter Your Desired Username");
		username = uin.nextLine();
		
		//Check that the username meets some basic parameters
		if (username.length() > 24){
			System.out.println("Usernames must not exceep 24 characters");
			return;
		}
		
		//Use the bannedList as a way to remove security risk characters or other undesirable inputs 
		for(int i=0; i<bannedList.length; i++){
			if (username.contains(bannedList[i])){
				System.out.println("Illegal character: " + bannedList[i] + " detected");
				return;
			}
		}
		
		//check that the user is not already in the table
		boolean exists = udao.checkUser(username);
		if(exists){
			System.out.println("The requested username is already in use");
			return;
		}
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
		
		System.out.println("Enter the site name");
		String sitename = uin.nextLine();
	
		//Use the bannedList as a way to remove security risk characters or other undesirable inputs 
		for(int i=0; i<bannedList.length; i++){
			if (sitename.contains(bannedList[i])){
				System.out.println("Illegal character: " + bannedList[i] + " detected");
				return;
			}
		}
		
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
				//TODO: is it possible to hide the input characters? find out.
				
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
