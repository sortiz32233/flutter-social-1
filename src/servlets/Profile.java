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
import data.PostData;
import data.Posts;

@WebServlet(name = "Profile", value = "/profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String uid, username, followedCount, followerCount, postCount, buttonLink, buttonType, followText;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean loggedIn = data.Cookies.isLoggedIn(request, response);
		uid = request.getQueryString();

		if (loggedIn) {
			String[] cookieData = data.Cookies.getCookieData(request, response);

			// Checks if UID exists
			try {
				if (Usernames.isUniqueUID(uid)) {
					request.setAttribute("uid", cookieData[1]);
					request.setAttribute("message", "User not found.");
					request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
					return;
				}
			} catch (Exception e) {
				request.setAttribute("uid", cookieData[1]);
				request.setAttribute("message", "Server error, failed to load user profile. Please try again.");
				request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
				return;
			}

			// Get follow counts (following, followers)
			List<List<String>> followData = new ArrayList<List<String>>();
			try {
				followData = Follows.getFollowData(uid);
			} catch (Exception e) {
				e.printStackTrace();
			}

			followedCount = "null";
			followerCount = "null";

			if (followData.size() == 2) {
				followedCount = "" + followData.get(0).size();
				followerCount = "" + followData.get(1).size();
			}

			// Get post count
			postCount = "null";
			try {
				postCount = "" + Posts.getPostCount(uid);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Get all posts from uid
			PostData[] userPosts = new PostData[0];
			try {
				userPosts = Posts.getUserPosts(uid);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Gets username
			try {
				username = Usernames.getUsername(uid);
			} catch (Exception e) {
				username = "null";
			}
			
			// See if logged in user is following or not
			followText = "Follow";
			buttonLink = "/follow?" + uid;
			buttonType = "primary";
			for (String f : followData.get(1))
				if (f.equals(cookieData[1])) {
					buttonLink = "/unfollow?" + uid;
					followText = "Unfollow";
					buttonType = "outline-danger";
					break;
				}

			request.setAttribute("buttonType", buttonType);
			request.setAttribute("buttonLink", buttonLink);
			request.setAttribute("followText", followText);
			request.setAttribute("loggedInUserId", cookieData[1]);
			request.setAttribute("uid", uid);
			request.setAttribute("username", username);
			request.setAttribute("emptyMessage", "This user hasn't made any posts yet.");
			request.setAttribute("followerCount", followerCount);
			request.setAttribute("followedCount", followedCount);
			request.setAttribute("postCount", postCount);
			request.setAttribute("postData", userPosts);
			request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
		} else {
			data.Cookies.clearCookies(request, response);
			request.setAttribute("error", "Please log in to view this profile.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
