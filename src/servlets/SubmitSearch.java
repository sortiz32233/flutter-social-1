package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import accounts.Usernames;
import data.Database;
import data.User;

@WebServlet(name = "SubmitSearch", value = "/submit-search")
public class SubmitSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String searchType, searchInput;
	private ArrayList<User> searchResults;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		searchType = request.getParameter("search-type");
		searchInput = request.getParameter("search-bar");
		String[] cookieData = data.Cookies.getCookieData(request, response);
    	request.setAttribute("uid", cookieData[1]);

		// Checks if search type is empty
		if (searchType.length() == 0) {
			request.setAttribute("searchError", "Please choose a search method.");
			request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
			return;
		}

		// Checks if search bar is empty
		if (searchInput.length() == 0) {
			request.setAttribute("searchError", "Your searchbar input cannot be empty.");
			request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
			return;
		}

		// Checks if username is valid, then runs search
		if (searchType.equals("username")) {
			if(!Usernames.isValidUsername(searchInput)) {
				request.setAttribute("searchError", "Invalid username.");
				request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
				return;
			}

			try {
				// Create a query against the collection
				Query query = Database.USERS.whereEqualTo("username", searchInput);
				// Retrieve query results asynchronously using query.get()
				ApiFuture<QuerySnapshot> querySnapshot = query.get();
				
				// Queries database and gets values
				searchResults = new ArrayList<User>();
				for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
					String uidResult = document.getString("uid");
					String usernameResult = document.getString("username");
					searchResults.add(new User(uidResult, usernameResult));
				}
			} catch (Exception e) {
				request.setAttribute("searchError", "Server error, search failed. Please try again");
				request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
				return;
			}
		// Checks if uid is valid, then runs search
		} else if (searchType.equals("uid")) {
			if (searchInput.length() != 24) {
				request.setAttribute("searchError", "Invalid user id.");
				request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
				return;
			}

			try {
				// Create a query against the collection
				Query query = Database.USERS.whereEqualTo("uid", searchInput.toUpperCase());
				// retrieve query results asynchronously using query.get()
				ApiFuture<QuerySnapshot> querySnapshot = query.get();
				
				// Queries database and gets values
				searchResults = new ArrayList<User>();
				for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
					String uidResult = document.getString("uid");
					String usernameResult = document.getString("username");
					searchResults.add(new User(uidResult, usernameResult));
				}
			} catch (Exception e) {
				request.setAttribute("searchError", "Server error, search failed. Please try again");
				request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
				return;
			}
		}

		// Returns search page with results
		request.setAttribute("emptyMessage", "No results found.");
		request.setAttribute("searchResults", searchResults);
		request.getRequestDispatcher("WEB-INF/search.jsp").forward(request, response);
		return;
	}
}
