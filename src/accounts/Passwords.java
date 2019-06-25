package accounts;

public class Passwords {
	// Check if password contains at least 3 different char types
	public static boolean isValidPassword(String pass) {
		if (pass.length() < 8)
			return false;
		
		int charTypes = 0;
		
		// Uppercase alpha
		if (pass.matches(".*[A-Z].*"))
			charTypes++;
		
		// Lowercase alpha
		if (pass.matches(".*[a-z].*"))
			charTypes++;
		
		// Digits
		if (pass.matches(".*\\d.*"))
			charTypes++;
		
		// Special chars
		if (pass.matches(".*[$&+,:;=?@#|'<>.^*()%!_-].*"))
			charTypes++;
		
		if(charTypes > 2)
			return true;
		
		return false;
	}
	
	// Generates Bcrypt password hash
	public static String hash(String password) {
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(10));
		return hashed;
	}
	
	// Generates Bcrypt salt
	public static boolean checkPassword(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}
}
