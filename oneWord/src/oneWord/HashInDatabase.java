package oneWord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HashInDatabase implements HashDAO{
	
	final static boolean DEBUG = true;
	
	public boolean isHash(User user, String site){
		
		
		
		
		
		return true;
	}
	//Create a new hashing if there is not one already
	public void makeHash(User user, String site){
		
		
		
		
		
		
	}
	//Get the existing hashing
	public String getHash(User user, String site){
		
		
		
		
		
		return "";
	}
	//create the password with the hash
	public String getPassword(User user, String site, String hash, String password){
		
		
		
		
		
		return "";
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
