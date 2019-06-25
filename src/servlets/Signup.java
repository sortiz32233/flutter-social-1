package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.firestore.DocumentReference;

import accounts.Emails;
import accounts.Passwords;
import accounts.Usernames;
import data.Database;

@WebServlet(name="Signup", value="/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String uid, email, username, hash;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Gets registration data from signup.jsp
		email = request.getParameter("signup-email");
		username = request.getParameter("signup-username");
		String pass = request.getParameter("signup-pass");
		String confirmPass = request.getParameter("signup-confirm-pass");

		// Checks if email already used
		try {
			if (Emails.emailIsUsed(email)) {
				request.setAttribute("error", "The email you entered is already in use.");
				request.setAttribute("username", username);
				request.getRequestDispatcher("signup.jsp").forward(request, response);

			// Checks username validity
			} else if (!Usernames.isValidUsername(username)) {
				request.setAttribute("error", "Error, your username must be 3-20 characters long and contain only letters, numbers, underscores, periods, or dashes.");
				request.setAttribute("email", email);
				request.getRequestDispatcher("signup.jsp").forward(request, response);

			// Checks if passwords match
			} else if (!pass.equals(confirmPass)) {	
				request.setAttribute("error", "Error, the passwords don't match.");
				request.setAttribute("email", email);
				request.setAttribute("username", username);
				request.getRequestDispatcher("signup.jsp").forward(request, response);

			// Checks password strength
			} else if (!Passwords.isValidPassword(pass)) {
				request.setAttribute("error", "Error, password must be at least 8 characters long and contain characters from at least 3 of the following categories:<br>"
						+ "  - Uppercase Letters (A-Z)<br>"
						+ "  - Lowercase Letters (a-z)<br>"
						+ "  - Numbers (0-9)<br>"
						+ "  - Symbols");
				request.setAttribute("email", email);
				request.setAttribute("username", username);
				request.getRequestDispatcher("signup.jsp").forward(request, response);

			// Generates salt, hash, and UID if inputs are valid, then sends verification email and saves to Firestore
			} else {
				uid = Usernames.generateUID();
				hash = Passwords.hash(pass);
				String verificationCode = Emails.generateVerificationCode();

				// Stores success/error message of email send
				String emailString = Emails.sendVerificationLink(email, username, verificationCode);

				// Displays error on sign up page if send failed
				if (emailString.equals("success")) {
					DocumentReference docRef = Database.USERS.document(uid);
					// Add document data using a hashmap
					Map<String, Object> data = new HashMap<>();
					data.put("uid", uid);
					data.put("email", email);
					data.put("username", username);
					data.put("hash", hash);
					data.put("verification", verificationCode);

					// Asynchronously write data
					docRef.set(data);

					// Sets up user data
					docRef = Database.FOLLOWS.document(uid);
					
					// Add document data using a hashmap
					data = new HashMap<>();
					data.put("uid", uid);
					
					ArrayList<String> empty = new ArrayList<String>();
					data.put("followers", empty);
					data.put("following", empty);

					// Asynchronously write data
					docRef.set(data);
					
					request.setAttribute("message", "Successfully registered! Please verify your email by clicking the link sent to " + email);
					request.getRequestDispatcher("success.jsp").forward(request, response);
				} else {
					request.setAttribute("error", emailString);
					request.setAttribute("username", username);
					request.getRequestDispatcher("signup.jsp").forward(request, response);
				}
			}
		} catch (InterruptedException e) {
			request.setAttribute("error", "Connection interupped, please try again later.");
			request.setAttribute("username", username);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		} catch (ExecutionException e) {
			request.setAttribute("error", "Server error, please come back later.");
			request.setAttribute("username", username);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}
}
