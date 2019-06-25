package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import accounts.Passwords;
import data.Database;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String email, password, usernameFromDB, uidFromDB, hashFromDB;
	private boolean emailIsVerified = false;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get data from Login.jsp
		email = request.getParameter("login-email");
		password = request.getParameter("login-password");

		// Checks Firestore for email and gets hash from database if found
		try {
			// Create a query against the collection.
			Query query = Database.USERS.whereEqualTo("email", email);
			// retrieve query results asynchronously using query.get()
			ApiFuture<QuerySnapshot> querySnapshot = query.get();
			
			// Queries database and gets values
			if (querySnapshot.get().getDocuments().size() == 1) {
				DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
				uidFromDB = document.getString("uid");
				usernameFromDB = document.getString("username");
				hashFromDB = document.getString("hash");
				if (document.get("verification") == null)
					emailIsVerified = true;
			} else {
				request.setAttribute("error", "Email not found.");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			request.setAttribute("error", "Server error, failed to log in. Please try again.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		// Set cookies if password matches hash
		if (Passwords.checkPassword(password, hashFromDB)) {
			if (emailIsVerified) {
				// Clears current cookies
				data.Cookies.clearCookies(request, response);

			    // Creates new cookies
				Cookie uidCookie = new Cookie("uid", uidFromDB);
				Cookie nameCookie = new Cookie("username", usernameFromDB);
				Cookie emailCookie = new Cookie("email", email);

			    // Set expiry date for cookies to 12 hours
				uidCookie.setMaxAge(60*60*12);
				nameCookie.setMaxAge(60*60*12);
				emailCookie.setMaxAge(60*60*12);

			    // Add cookies to browser
				response.addCookie(uidCookie);
			    response.addCookie(nameCookie);
			    response.addCookie(emailCookie);
			    
			    // Redirect to login page
			    response.sendRedirect("/my-profile");
			} else {
				request.setAttribute("error", "Please verify your email (check your email for your verification link).");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("error", "Incorrect password.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
