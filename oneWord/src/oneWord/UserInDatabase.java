package oneWord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserInDatabase implements UserDAO {
	
	public static boolean DEBUG = true;
	//log in the user matching the user name and password combination
	public User login(String username, String password){
		System.out.println("Test");
		
		
		//Make the connection
		Connection con = null;
		try{
			Class.forName("org.postgresql.Driver");
			//con = DriverManager.getConnection("jdbc:postgresql://oneword.cmtfz6kl9wdd.us-east-1.rds.amazonaws.com:5432/database?tcpKeepAlive=true");
			//con = DriverManager.getConnection("jdbc:postgresql://oneword.cmtfz6kl9wdd.us-east-1.rds.amazonaws.com:5432/?user=jackmana&password=pscmazilcsg&tcpKeepAlive=true");
			con = DriverManager.getConnection("jdbc:postgresql://oneword.cmtfz6kl9wdd.us-east-1.rds.amazonaws.com:5432/postgres?user=jackmana&password=pscmazilcsg");
			System.out.println("looks good");
		}
		catch(ClassNotFoundException e){
			if (DEBUG){
				System.err.println("Class not found exception: " + e.getMessage());
			}
			con = null;
		}
		catch(SQLException e){
			if(DEBUG){
				System.err.println("SQLException: " + e.getMessage());
			}
			con = null;
		}
		
		
		
		return new User("adam", "jackman", 1);
	}
	
	//register the user with a user name and a password
	public User registerUser(String username, String password){
		System.out.println("Test");
		
		
		
		
		
		return new User("adam", "jackman", 1);
	}
	
	//Checks that the user exists in the database already
	public boolean checkUser(String username){
		
		
		
		
		
		System.out.println("Test");
		return true;
	}
	
}
