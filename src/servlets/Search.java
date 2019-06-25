package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="Search", value="/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean loggedIn = data.Cookies.isLoggedIn(request, response);

	    if (loggedIn) {
	    	String[] cookieData = data.Cookies.getCookieData(request, response);
	    	request.setAttribute("uid", cookieData[1]);
			request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
	    } else {
	    	data.Cookies.clearCookies(request, response);
	    	request.setAttribute("error", "Please log in to search for users.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
	    }
	}
}
