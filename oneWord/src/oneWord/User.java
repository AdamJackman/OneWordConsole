package oneWord;

public class User {
	//The user will be used as a data holder for information on the logged in user 
	private String username;
	private String password;
	private int uID;
	
	//Constructor
	public User(String username, String password, int uID){
		this.username = username;
		this.password = password;
		this.uID = uID;
		
	}
	
	//Simple get/sets, for now all are available, TODO: Refine
	public String getPassword(){
		return this.password;
		
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public int getUID(){
		return this.uID;
	}
	
	public void setUID(int ID){
		this.uID=ID;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
}
