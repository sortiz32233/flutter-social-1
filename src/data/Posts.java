package data;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

public class Posts {
	// Returns post count
	public static int getPostCount(String uid) throws InterruptedException, ExecutionException {
		// Create a query against the collection.
		Query query = Database.POSTS.whereEqualTo("uid", uid);
		// Retrieve query results asynchronously using query.get()
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		// Queries database and gets values
		// Returns number of posts created by the specified uid
		return querySnapshot.get().getDocuments().size();
	}
	
	// Make a new post
	public static void makePost(String uid, String username, String title, String content) throws InterruptedException, ExecutionException {
		// Generate post ID
		String pid = generatePID();
		DocumentReference docRef = Database.POSTS.document(pid);
		// Add document data using a hashmap
		Map<String, Object> data = new HashMap<>();
		data.put("uid", uid);
		data.put("username", username);
		data.put("pid", pid);
		data.put("title", title);
		data.put("content", content);
		data.put("date", PostDate.getCurrentDate());
		data.put("likes", "0");

		// Asynchronously write data
		docRef.set(data);
	}
	
	// Get all posts from uid
	public static PostData[] getUserPosts(String uid) throws InterruptedException, ExecutionException, ParseException {
		ArrayList<PostData> posts = new ArrayList<PostData>();
		
		// Create a query against the collection.
		Query query = Database.POSTS.whereEqualTo("uid", uid);
		// Retrieve query results asynchronously using query.get()
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		
		// Queries database and gets values
		ArrayList<DocumentSnapshot> documents = new ArrayList<DocumentSnapshot>();
		for (DocumentSnapshot ds : querySnapshot.get().getDocuments()) {
			documents.add(ds);
		}
		
		for (DocumentSnapshot ds : documents) {
			String username = (String) ds.get("username");
			String pid = (String) ds.get("pid");
			String title = (String) ds.get("title");
			String content = (String) ds.get("content");
			String date = (String) ds.get("date");
			String likes = (String) ds.get("likes");
			
			date = PostDate.convertToLocal(date);
			
			posts.add(new PostData(uid, username, pid, title, content, date, likes));
		}
		
		// Converts to array
		PostData[] postArr = new PostData[posts.size()];
		for (int i = 0; i < postArr.length; i++)
			postArr[i] = posts.get(i);
		
		return postArr;
	}

	// Generate random post ID
	public static String generatePID() throws InterruptedException, ExecutionException {
		StringBuilder pidBuilder = new StringBuilder("PID_");

		for (int i = 0; i < 20; i++) {
			pidBuilder.append((int) Math.floor(Math.random()*10));
		}
		
		String pid = pidBuilder.toString();
		
		// Create a query against the collection.
		Query query = Database.POSTS.whereEqualTo("pid", pid);
		// Retrieve query results asynchronously using query.get()
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		if (querySnapshot.get().getDocuments().size() > 0)
			pid = generatePID();
		
		return pid;
	}
}
