package servlets;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Posts;

@WebServlet(name="SubmitPost", value="/submit-post")
public class SubmitPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String title, content, postCount;
	// Makes a post and writes to database
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean loggedIn = data.Cookies.isLoggedIn(request, response);

	    if (loggedIn) {
	    	String[] cookieData = data.Cookies.getCookieData(request, response);

	    	// Set cookie data as attributes
	    	request.setAttribute("uid", cookieData[1]);
	    	request.setAttribute("username", cookieData[2]);

	    	// Get post counts
	    	postCount = "null";
	    	try {
				postCount = "" + Posts.getPostCount(cookieData[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	// Gets title and content from make post form
	    	title = request.getParameter("post-title");
			content = request.getParameter("post-content");

			// Checks if title is too short or too long
			if (title.length() == 0) {
				request.setAttribute("posterror", "The title cannot be empty.");
				request.setAttribute("content", content);
				request.setAttribute("posts", postCount);
				request.getRequestDispatcher("WEB-INF/my-posts.jsp").forward(request, response);
				return;
			} else if (title.length() > 30) {
				request.setAttribute("posterror", "Your title is " + title.length() + " characters long. (Maximum is 30 characters)");
				request.setAttribute("title", title);
				request.setAttribute("content", content);
				request.setAttribute("posts", postCount);
				request.getRequestDispatcher("WEB-INF/my-posts.jsp").forward(request, response);
				return;
			}

			// Checks if content is too short or too long
			if (content.length() == 0) {
				request.setAttribute("posterror", "The content cannot be empty.");
				request.setAttribute("title", title);
				request.setAttribute("posts", postCount);
				request.getRequestDispatcher("WEB-INF/my-posts.jsp").forward(request, response);
				return;
			} else if (content.length() > 300) {
				request.setAttribute("posterror", "Your post is " + content.length() + " characters long. (Maximum is 300 characters)");
				request.setAttribute("title", title);
				request.setAttribute("content", content);
				request.setAttribute("posts", postCount);
				request.getRequestDispatcher("WEB-INF/my-posts.jsp").forward(request, response);
				return;
			}
	    	
			// Makes the post
			try {
				Posts.makePost(cookieData[1], cookieData[2], title, content);
			} catch (InterruptedException e1) {
				request.setAttribute("posterror", "Server error, connection interuppted. Please try again.");
				request.setAttribute("title", title);
				request.setAttribute("content", content);
				request.setAttribute("posts", postCount);
				request.getRequestDispatcher("WEB-INF/my-posts.jsp").forward(request, response);
				return;
			} catch (ExecutionException e1) {
				request.setAttribute("posterror", "Server error, could not submit post. Please try again.");
				request.setAttribute("title", title);
				request.setAttribute("content", content);
				request.setAttribute("posts", postCount);
				request.getRequestDispatcher("WEB-INF/my-posts.jsp").forward(request, response);
				return;
			}

			// Redirect to my-posts page
			response.sendRedirect("/my-posts");
	    } else {
	    	data.Cookies.clearCookies(request, response);
	    	request.setAttribute("error", "Please log in to make a post.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
	    }
	}
}
