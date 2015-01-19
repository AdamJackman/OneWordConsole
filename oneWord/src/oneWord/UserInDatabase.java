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
		Connection con = dbcon();
		//Connection created, continue with login
		try{
			String existQuery = "SELECT * FROM users WHERE username=?;";
			PreparedStatement existPS = con.prepareStatement(existQuery);
			existPS.setString(1, username);
			ResultSet rs = existPS.executeQuery();
			while (rs.next()){
				if (rs.getString("username").equals(username)){
					int uid = rs.getInt("userid");
					con.close();
					rs.close();
					existPS.close();
					//return the User information
					return new User(username, uid);
				}
			}
			con.close();
			rs.close();
			existPS.close();
		}
		catch(SQLException e){
			if (DEBUG){
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		//No match
		return null;
	}
	
	//register the user with a user name
	public User registerUser(String username){
		
		Connection con = dbcon();
		//As the user is not in the database we now add them
		try{
			String insertQuery = "INSERT INTO users(username) VALUES (?);";
			PreparedStatement insertPS = con.prepareStatement(insertQuery);
			insertPS.setString(1, username);
			insertPS.executeUpdate();
			insertPS.close();
			con.close();			
			//Insert is successful therefore create the new user object and return it
			return login(username);
		}
		catch(SQLException e){
			if (DEBUG){
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		//catching returning null
		return null;	
	}
	
	//Checks that the user exists in the database already
	public boolean checkUser(String username){
		
		//Make the connection		
		Connection con = dbcon();
		//Connection created, continue with login
		try{
			String existQuery = "SELECT * FROM users WHERE username=?;";
			PreparedStatement existPS = con.prepareStatement(existQuery);
			existPS.setString(1, username);
			ResultSet rs = existPS.executeQuery();
			while (rs.next()){
				if (rs.getString("username").equals(username)){
					con.close();
					rs.close();
					existPS.close();
					//return the User information
					return true;
				}
			}
			con.close();
			rs.close();
			existPS.close();
		}
		catch(SQLException e){
			if (DEBUG){
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		//No match
		return false;
	}
	
	public Connection dbcon(){
		Connection con = null;
		try{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://oneword.cmtfz6kl9wdd.us-east-1.rds.amazonaws.com:5432/postgres?user=jackmana&password=pscmazilcsg");
			return con;
		}
		catch(ClassNotFoundException e){
			if (DEBUG){
				System.err.println("Class not found exception: " + e.getMessage());
			}
			con = null;
			return con;
		}
		catch(SQLException e){
			if(DEBUG){
				System.err.println("SQLException: " + e.getMessage());
			}
			con = null;
			return con;
		}
	}
	
}
