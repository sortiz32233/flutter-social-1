package accounts;

import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import data.Database;

public class Usernames {
	// Checks if username is alphanumeric (plus periods, dashes, and underscores) and length 3-20
	public static boolean isValidUsername(String username) {
		if (username.matches("^[a-zA-Z0-9_.-]{3,20}$"))
			return true;
		return false;
	}
	
	// Gets username from uid
	public static String getUsername(String uid) throws InterruptedException, ExecutionException {
		// Create a query against the collection.
		Query query = Database.USERS.whereEqualTo("uid", uid);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		if (querySnapshot.get().getDocuments().size() > 0) {
			DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
			return document.getString("username");
		} else
			return null;
	}
	
	// Checks if UID is in the database
	public static boolean isUniqueUID(String uid) throws InterruptedException, ExecutionException {
		// Create a query against the collection.
		Query query = Database.USERS.whereEqualTo("uid", uid);
		// Retrieve query results asynchronously using query.get()
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		if (querySnapshot.get().getDocuments().size() > 0)
			return false;
		return true;
	}
	
	// Generates 20 digit random user ID
	public static String generateUID() throws InterruptedException, ExecutionException {
		StringBuilder uidBuilder = new StringBuilder("UID_");

		for (int i = 0; i < 20; i++) {
			uidBuilder.append((int) Math.floor(Math.random()*10));
		}

		String uid = uidBuilder.toString();

		if (!isUniqueUID(uid))
			uid = generateUID();

		return uid;
	}
}
