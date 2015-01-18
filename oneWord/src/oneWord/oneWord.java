package oneWord;

import java.util.Scanner;

public class oneWord {
	//The main method will be ran here
	public static void main(String args[]){
		
		//Instantiate the type of DAO
		UserDAO udao = new UserInTest();
		HashDAO hdao = new HashInTest();
		Scanner uin = new Scanner(System.in);
		String username, password;
		
		//Create the interface
		//TODO: Create some sort of GUI
		boolean logged = false;
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
				//The User is not logged in
				System.out.println("Enter Username");
				username = uin.nextLine();
				System.out.println("Enter Password");
				password = uin.nextLine();
				//close the reader

				
				System.out.println("The variables recieved are: " + username + " " + password);
				
				//Attempt user log in
				User me = udao.login(username, password);
				if (me == null){
					//The Log in was unsuccessful - do not change the logged variable
					System.out.println("Log in unsuccessful - ");
					//TODO: Add counters to measure attempts on a certain username?
				}
				else{
					//Artifical switch
					//The User is now logged in and the data is stored in the me variable
					logged = true;

				}
				
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
}
