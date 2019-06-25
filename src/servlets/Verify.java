package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import data.Database;

@WebServlet(name="Verify", value="/verify")
public class Verify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String verificationCode = request.getQueryString();

		try {
			// Create a query against the collection.
			Query query = Database.USERS.whereEqualTo("verification", verificationCode);
			// Retrieve query results asynchronously using query.get()
			ApiFuture<QuerySnapshot> querySnapshot = query.get();

			// Queries database and removes verify field if found
			if (querySnapshot.get().getDocuments().size() == 1) {
				DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
				String id = document.getId();
				
				DocumentReference docRef = Database.USERS.document(id);
				Map<String, Object> updates = new HashMap<>();
				updates.put("verification", FieldValue.delete());
				
				// Update and delete the "verification" field in the document
				docRef.update(updates);
				
				request.setAttribute("message", "Email verified! Log in <a href=\"https://flutter-social-platform.appspot.com/login.jsp\">here</a>.");
				request.getRequestDispatcher("success.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "Error, invalid verification link.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("message", "Error, failed to verify. Please try again.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}
