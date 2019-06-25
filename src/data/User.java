package data;

public class User {
	private String uid, username;

	public User(String uid, String username) {
		this.uid = uid;
		this.username = username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUID(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public String getUID() {
		return uid;
	}

	public String toString() {
		return uid;
	}
}
