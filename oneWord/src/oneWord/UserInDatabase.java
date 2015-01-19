package oneWord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInDatabase implements UserDAO {
	
	public static boolean DEBUG = true;
	
	
	//log in the user matching the user name and password combination
	public User login(String username){	
		//Make the connection
		Connection con = null;
		try{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://oneword.cmtfz6kl9wdd.us-east-1.rds.amazonaws.com:5432/postgres?user=jackmana&password=pscmazilcsg");
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
		try{
			//TODO: Currently not checking password. Not sure if want one yet
			String existQuery = "SELECT * FROM users WHERE username=?;";
			PreparedStatement existPS = con.prepareStatement(existQuery);
			existPS.setString(1, username);
			ResultSet rs = existPS.executeQuery();
			while (rs.next()){
				//TODO: Change this to a password match??
				if (rs.getString("username").equals(username)){
					int uid = rs.getInt("userid");
					con.close();
					//return the User information
					
					return new User(username, uid);
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
		//No match
		con = null;
		return null;
	}
	
	//register the user with a user name and a password
	public User registerUser(String username){
		System.out.println("Test");
		
		
		
		
		
		return new User("adam", 1);
	}
	
	//Checks that the user exists in the database already
	public boolean checkUser(String username){
		
		
		
		
		
		System.out.println("Test");
		return true;
	}
	
}
