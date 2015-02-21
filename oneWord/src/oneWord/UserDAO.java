package oneWord;

public interface UserDAO {
	
	//log in the user matching the username, no password required
	public User login(String username);
	
	//register the user with a username to the database
	public User registerUser(String username);
	
	//Checks that the user exists in the database already
	public boolean checkUser(String username);
	
}