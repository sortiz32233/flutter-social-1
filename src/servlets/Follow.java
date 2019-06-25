package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accounts.Usernames;
import data.Follows;

@WebServlet(name = "Follow", value = "/follow")
public class Follow extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String uidCurrentUser;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uidToFollow = request.getQueryString();
		boolean loggedIn = data.Cookies.isLoggedIn(request, response);

		if (loggedIn) {
			String[] cookieData = data.Cookies.getCookieData(request, response);
			uidCurrentUser = cookieData[1];

			try {
				if (!Usernames.isUniqueUID(uidToFollow)) {
					Follows.followUser(uidCurrentUser, uidToFollow);
				} else {
					request.setAttribute("uid", cookieData[1]);
					request.setAttribute("message", "User not found.");
					request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
				}
			} catch (Exception e) {
				request.setAttribute("uid", cookieData[1]);
				request.setAttribute("message", "Server error, failed to follow user. Please try again.");
				request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
			}

			response.sendRedirect("/following?" + cookieData[1]);
		} else {
			data.Cookies.clearCookies(request, response);
			request.setAttribute("error", "Please log in to follow users.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
