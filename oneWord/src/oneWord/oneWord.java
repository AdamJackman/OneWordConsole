package oneWord;

public class oneWord {
	//The main method will be ran here
	public static void main(String args[]){
		System.out.println("Hello World");
		
		//Instantiate the type of DAO
		UserDAO udao = new UserInTest();
		HashDAO hdao = new HashInTest();
		
		System.out.println(udao.checkUser("test"));
		System.out.println(hdao.isHash(new User("", "", 1), "Test"));		
	}
}
