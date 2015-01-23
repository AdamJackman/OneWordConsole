package oneWord;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Test;

public class TestOneWord {
	
	public static UserDAO udao = new UserInDatabase();
	public static HashDAO hdao = new HashInDatabase();

	@Test
	public void testRegisterAndCheckUser() {
		User me = udao.registerUser("TestName");
		boolean exists = udao.checkUser("TestName");
		assertTrue(exists);
		
		//NOW CLEARING ENTRY FROM DB
		try {			
			//Insert the store salt into the table
			Connection con = dbcon();
			String insertQuery = "DELETE FROM users WHERE username = ?;";
			PreparedStatement insertPS = con.prepareStatement(insertQuery);
			insertPS.setString(1, "TestName");
			insertPS.executeUpdate();
			insertPS.close();
			con.close();		
		}
		catch(SQLException e){
			System.err.println("SQLException: " + e.getMessage());
		}
		//TEST EXISTENCE AGAIN
		exists = udao.checkUser("TestName");
		assertFalse(exists);		
	}

	@Test
	public void testGetAndMakeHash(){
		//test the get and make hash functions
		//Always in database
		String testSite = "testSite";
		User me = udao.login("adam");
		User me2 = udao.login("alex");
		//Check users are not the same
		Assert.assertNotSame(me, me2);
		
		
		hdao.makeHash(me, testSite);
		hdao.makeHash(me2, testSite);
		String hash = hdao.getHash(me, testSite);
		String hash2 = hdao.getHash(me2, testSite);
		//Check the hashes are unique
		assertNotEquals(hash, hash2);
		
		//NOW CLEARING ENTRY FROM DB
		try {			
			//Insert the store salt into the table
			Connection con = dbcon();
			String insertQuery = "DELETE FROM saltstore WHERE userid = ? AND site= ?;";
			PreparedStatement insertPS = con.prepareStatement(insertQuery);
			insertPS.setInt(1, 1);
			insertPS.setString(2, testSite);
			insertPS.executeUpdate();
			insertPS.close();
			
			String insertQuery2 = "DELETE FROM saltstore WHERE userid = ? AND site= ?;";
			PreparedStatement insertPS2 = con.prepareStatement(insertQuery);
			insertPS2.setInt(1, 2);
			insertPS2.setString(2, testSite);
			insertPS2.executeUpdate();
			insertPS2.close();
						
			con.close();		
		}
		catch(SQLException e){
			System.err.println("SQLException: " + e.getMessage());
		}
		
		hash = hdao.getHash(me, testSite);
		hash2 = hdao.getHash(me2, testSite);
		//assert that the getHash is now seeing null
		assertNull(hash);
		assertNull(hash2);
	}
	
	@Test
	public void testHashEquality(){
		//Always in database
		User me = udao.login("adam");
		String sitename = "facebook";
		String hash = hdao.getHash(me, sitename);
		String password = "test";
		
		String hashed = hdao.getPassword(me, sitename, hash, password);
		String hashed2 = hdao.getPassword(me, sitename, hash, password);
		Assert.assertEquals(hashed, hashed2);
		
	}
	@Test
	public void testHashInEquality(){
		//Check that 2 different users will get different passwords
		//Always in database adam and alex
		User me = udao.login("adam");
		User me2 = udao.login("alex");
		String sitename = "facebook";
		String hash = hdao.getHash(me, sitename);
		String hash2 = hdao.getHash(me2, sitename);
		String password = "test";
		
		String hashed = hdao.getPassword(me, sitename, hash, password);
		String hashed2 = hdao.getPassword(me2, sitename, hash2, password);
		//Check that the passwords are not the same even with same 
		assertNotEquals(hashed, hashed2);
	}
	
	
	
	public Connection dbcon(){
		boolean DEBUG=true;
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
