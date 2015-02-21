package oneWord;

public interface HashDAO {
	
	//Check whether the current user has a hashed word for the site
	public boolean isHash(User user, String site);
	
	//Create a new hashing if there is not one already
	public void makeHash(User user, String site);
	
	//Get the existing hashing
	public String getHash(User user, String site);
	
	//create the password with the hash
	public String getPassword(User user, String site, String hash, String password);
	
}
