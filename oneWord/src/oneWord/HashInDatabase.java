package oneWord;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class HashInDatabase implements HashDAO{
	
	final static boolean DEBUG = true;
	
	public boolean isHash(User user, String site){
		//Check that the user has a hash for the site
		
		//Make the connection		
		Connection con = dbcon();
		try{
			String existQuery = "SELECT * FROM saltstore WHERE userid=? and site=?;";
			PreparedStatement existPS = con.prepareStatement(existQuery);
			//set the userid and site
			existPS.setInt(1, user.getUID());
			existPS.setString(2, site);
			
			ResultSet rs = existPS.executeQuery();
			while (rs.next()){
				// Extra safety layer - Keep till transactions 
				if (rs.getString("site").equals(site)){
					con.close();
					rs.close();
					existPS.close();
					//return true as found
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
	
	//Create a new hashing if there is not one already
	public void makeHash(User user, String site){
		// Use a random word generator
		try {			
			String randStore = randWordGen(20);
			//Insert the store salt into the table
			Connection con = dbcon();
			//TODO: Unhardcode this length later, user might want a specific length
			String insertQuery = "INSERT INTO saltstore(userid, site, salt, len) VALUES (?, ?, ?, 12);";
			PreparedStatement insertPS = con.prepareStatement(insertQuery);
			insertPS.setInt(1, user.getUID());
			insertPS.setString(2, site);
			insertPS.setString(3, randStore);
			insertPS.executeUpdate();
			insertPS.close();
			con.close();		
			return;
		}
		catch(SQLException e){
			if (DEBUG){
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		System.out.println("Critical Hash Failure");	
	}
	
	//Get the existing hashing
	public String getHash(User user, String site){
		
		//going to be quite similar to the get hash
		//Make the connection		
		Connection con = dbcon();
		try{
			String existQuery = "SELECT * FROM saltstore WHERE userid=? and site=?;";
			PreparedStatement existPS = con.prepareStatement(existQuery);
			//set the userid and site
			existPS.setInt(1, user.getUID());
			existPS.setString(2, site);
			
			ResultSet rs = existPS.executeQuery();
			while (rs.next()){
				// Extra safety layer - Keep till transactions
				if (rs.getString("site").equals(site)){
					String res = rs.getString("salt");
					con.close();
					rs.close();
					existPS.close();
					return res;
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
		return null;
	}

	//create the password with the hash
	public String getPassword(User user, String site, String hash, String password){
		
		//Take the salt from and the password and create the real hash
		String toEnc = password + hash;
				
		System.out.println(toEnc);
		
		//String x = toEnc;
		//Using Sha1 for the encryption
		java.security.MessageDigest d = null;
	    try {
			d = java.security.MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    d.reset();
	    d.update(toEnc.getBytes());
	    byte [] encrypted = d.digest();
	    //Problem here is that the encryption returns bytes.
	    //I have altered my random word generator to produce a String using the bytes
	    String s = new String(encrypted);	    
	    String randoAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdeghijklmnopqrstuvwxyz=-!@#$%^&*(){}|:123456789";
		int modlen = randoAlphabet.length();
	    String finishedPass = "";
	    for (int i=0; i<s.length(); i++){
	    	int val = (int)s.charAt(i);
	    	int modspot = val % modlen;
			char curr = randoAlphabet.charAt(modspot);
			finishedPass = finishedPass + curr;
	    }

		return finishedPass;
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
	
	
	/**
	 * Ok, lets make a random word generator:
	 * len: What is the cap
	 */
	public String randWordGen(int len){
		//Here are the possibilities
		String randoAlphabet = "abcdeghijklmnopqrstuvwxyz!@#$%^&*(){}|:123456789";
		int modlen = randoAlphabet.length();
		
		String randWord = "";
		for(int i=0; i<len; i++){
			//Make a random generator
			Random generator = new Random();
			//Big int modded 
			//TODO: Not sure if this is better than a 0,48 rand or not, probably not
			int spot = generator.nextInt(1000000);
			int modspot = spot % modlen;
			char curr = randoAlphabet.charAt(modspot);
			randWord = randWord + curr;
		}
		return randWord;
	}
}
