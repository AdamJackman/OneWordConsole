package oneWord;

public class HashInTest implements HashDAO{

	//Check whether the current user has a hashed word for the site
	public boolean isHash(User user, String site){
		System.out.println("Hash Test");
		return false;
	}
	//Create a new hashing if there is not one already
	public void makeHash(User user, String site){
		System.out.println("Hash Test");
	}
	//Get the existing hashing
	public String getHash(User user, String site){
		System.out.println("Hash Test");
		return "abcdef";
	}
	
	public String getPassword(User user, String site, String hash, String password){
		System.out.println("Hash Test");
		return "abcdef";
	}
	
	
}
