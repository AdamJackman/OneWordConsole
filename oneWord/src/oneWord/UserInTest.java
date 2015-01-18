package oneWord;

public class UserInTest implements UserDAO {

	//log in the user matching the user name and password combination
	public User login(String username, String password){
		System.out.println("Test");
		return null;
	}
	
	//register the user with a user name and a password
	public User registerUser(String username, String password){
		System.out.println("Test");
		return new User("", "", 1);
	}
	
	//Checks that the user exists in the database already
	public boolean checkUser(String username){
		System.out.println("Test");
		return true;
	}
	
	
}
