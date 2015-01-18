package oneWord;

public interface UserDAO {
	
	//log in the user matching the username and password combination
	public User login(String username, String password);
	
	//register the user with a username and a password
	public User registerUser(String username, String password);
	
	//Checks that the user exists in the database already
	public boolean checkUser(String username);
	
}