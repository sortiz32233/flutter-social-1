package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Follows;
import data.Posts;

@WebServlet(name="MyProfile", value="/my-profile")
public class MyProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean loggedIn = data.Cookies.isLoggedIn(request, response);

	    if (loggedIn) {
	    	String[] cookieData = data.Cookies.getCookieData(request, response);

	    	request.setAttribute("email", cookieData[0]);
	    	request.setAttribute("uid", cookieData[1]);
	    	request.setAttribute("username", cookieData[2]);

	    	// Get follow counts (following, followers)
	    	List<List<String>> followData = new ArrayList<List<String>>();
			try {
				followData = Follows.getFollowData(cookieData[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	String followingCount = "null";
	    	String followersCount = "null";

	    	if (followData.size() == 2) {
	    		followingCount = "" + followData.get(0).size();
	    		followersCount = "" + followData.get(1).size();
	    	}
	    	
	    	// Get post counts
	    	String postCount = "null";
	    	try {
				postCount = "" + Posts.getPostCount(cookieData[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	request.setAttribute("followers", followersCount);
	    	request.setAttribute("followed", followingCount);
	    	request.setAttribute("posts", postCount);
			request.getRequestDispatcher("WEB-INF/my-profile.jsp").forward(request, response);
	    } else {
	    	data.Cookies.clearCookies(request, response);
	    	request.setAttribute("error", "Please log in to view your profile.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
	    }
	}
}
