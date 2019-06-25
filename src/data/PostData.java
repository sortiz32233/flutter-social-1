package data;

public class PostData {
	private String uid, username, pid, title, content, date, likes;
	
	public PostData() {
		this("", "", "", "", "", "", "");
	}
	
	public PostData(String uid, String username, String pid, String title, String content, String date, String likes) {
		this.uid = uid;
		this.username = username;
		this.pid = pid;
		this.title = title;
		this.content = content;
		this.date = date;
		this.likes = likes;
	}
	
	public void setUID(String uid) {
		this.uid = uid;
	}

	public void setUsername(String username) {
		this.uid = username;
	}

	public void setPID(String pid) {
		this.pid = pid;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setContent(String content) {
		this.content = uid;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setLikes(String likes) {
		this.likes = likes;
	}
	
	public String getUID() {
		return uid;
	}

	public String getUsername() {
		return username;
	}

	public String getPID() {
		return pid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getLikes() {
		return likes;
	}
	
	public String toString() {
		return pid;
	}
}
