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

@WebServlet(name="Following", value="/following")
public class Following extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String followingCount, displayUsername;
	ArrayList<User> followingUsers;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getQueryString();
		boolean loggedIn = data.Cookies.isLoggedIn(request, response);

		if (loggedIn) {
			String[] cookieData = data.Cookies.getCookieData(request, response);

			request.setAttribute("loggedInUid", cookieData[1]);
			request.setAttribute("loggedInUsername", cookieData[2]);

			// Get following count and followed uid list
	    	List<List<String>> followData = new ArrayList<List<String>>();
			try {
				followData = Follows.getFollowData(uid);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	followingCount = "null";

	    	if (followData.size() == 2)
	    		followingCount = "" + followData.get(0).size();
	    	
	    	// Set user object list
	    	followingUsers = new ArrayList<User>();
	    	for (String followingUID : followData.get(0)) {
	    		String name;
	    		
	    		try {
					name = Usernames.getUsername(followingUID);
				} catch (Exception e) {
					name = "null";
				}
	    		
	    		followingUsers.add(new User(followingUID, name));
	    	}

	    	// Set display username
	    	try {
				displayUsername = Usernames.getUsername(uid);
			} catch (Exception e) {
				displayUsername = "null";
			}

	    	// Set page attributes and load page
	    	request.setAttribute("displayUsername", displayUsername);
	    	request.setAttribute("followingCount", followingCount);
	    	request.setAttribute("followingUsers", followingUsers);
			request.getRequestDispatcher("WEB-INF/following.jsp").forward(request, response);
		} else {
			data.Cookies.clearCookies(request, response);
			request.setAttribute("error", "Please log in to view followed users.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
