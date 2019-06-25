package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accounts.Usernames;
import data.Follows;
import data.User;

@WebServlet(name="Followers", value="/followers")
public class Followers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String followerCount, displayUsername;
	ArrayList<User> followerUsers;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getQueryString();
		boolean loggedIn = data.Cookies.isLoggedIn(request, response);

		if (loggedIn) {
			String[] cookieData = data.Cookies.getCookieData(request, response);

			request.setAttribute("loggedInUid", cookieData[1]);
			request.setAttribute("loggedInUsername", cookieData[2]);

			// Get follower count and followers uid list
	    	List<List<String>> followData = new ArrayList<List<String>>();
			try {
				followData = Follows.getFollowData(uid);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	followerCount = "null";

	    	if (followData.size() == 2)
	    		followerCount = "" + followData.get(1).size();
	    	
	    	// Set user object list
	    	followerUsers = new ArrayList<User>();
	    	for (String followerUID : followData.get(1)) {
	    		String name;
	    		
	    		try {
					name = Usernames.getUsername(followerUID);
				} catch (Exception e) {
					name = "null";
				}
	    		
	    		followerUsers.add(new User(followerUID, name));
	    	}

	    	// Set display username
	    	try {
				displayUsername = Usernames.getUsername(uid);
			} catch (Exception e) {
				displayUsername = "null";
			}

	    	// Set page attributes and load page
	    	request.setAttribute("displayUsername", displayUsername);
	    	request.setAttribute("followerCount", followerCount);
	    	request.setAttribute("followerUsers", followerUsers);
			request.getRequestDispatcher("WEB-INF/followers.jsp").forward(request, response);
		} else {
			data.Cookies.clearCookies(request, response);
			request.setAttribute("error", "Please log in to view followers.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
