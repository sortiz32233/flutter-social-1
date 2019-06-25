package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.PostData;
import data.Posts;

@WebServlet(name = "MyPosts", value = "/my-posts")
public class MyPosts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String postCount;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean loggedIn = data.Cookies.isLoggedIn(request, response);

		if (loggedIn) {
			String[] cookieData = data.Cookies.getCookieData(request, response);

			request.setAttribute("uid", cookieData[1]);
			request.setAttribute("username", cookieData[2]);

			// Get post counts
			postCount = "null";
			try {
				postCount = "" + Posts.getPostCount(cookieData[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Get all posts from uid
			PostData[] userPosts = new PostData[0];
			try {
				userPosts = Posts.getUserPosts(cookieData[1]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			request.setAttribute("postdata", userPosts);
			request.setAttribute("posts", postCount);
			request.setAttribute("emptymessage", "Currently, you have no posts. Try creating one above!");
			request.getRequestDispatcher("WEB-INF/my-posts.jsp").forward(request, response);
		} else {
			data.Cookies.clearCookies(request, response);
			request.setAttribute("error", "Please log in to view your posts.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
