package oneWord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInDatabase implements UserDAO {
	
	public static boolean DEBUG = true;
	
	
	//log in the user matching the user name and password combination
	public User login(String username, String password){	
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
		//Connection created, continue with login
		
		
		//First check if the user is already in the database
		//SELECT * FROM user WHERE email= email
		try{
			String existQuery = "SELECT * FROM users WHERE username=?;";
			PreparedStatement existPS = con.prepareStatement(existQuery);
			existPS.setString(1, username);
			ResultSet rs = existPS.executeQuery();
			while (rs.next()){
				//TODO: Change this to a password match
				System.out.println();
				System.out.println(rs.getString("username"));
				if (rs.getString("username").equals(username)){
					//TODO: change the functionality to return a user
					con.close();
					return null; // user must exist, so return null
				}
			}
			rs.close();
			existPS.close();
		}
		catch(SQLException e){
			if (DEBUG){
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		
		
		
		
		
		//closing the con before the return
		con = null;
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
