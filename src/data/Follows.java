package data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

public class Follows {
	// Returns list: {followed uids, follower uids}
	@SuppressWarnings("unchecked")
	public static List<List<String>> getFollowData(String uid) throws Exception {
		List<List<String>> data = new ArrayList<List<String>>();

		// Create a query against the collection.
		Query query = Database.FOLLOWS.whereEqualTo("uid", uid);
		// Retrieve query results asynchronously using query.get()
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		// Queries database and gets values
		if (querySnapshot.get().getDocuments().size() == 1) {
			DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
			List<String> followers = (List<String>) document.get("followers");
			List<String> following = (List<String>) document.get("following");
			data.add(following);
			data.add(followers);
		}

		return data;
	}

	// Adds user to following list and adds to follower list of user being followed
	@SuppressWarnings("unchecked")
	public static void followUser(String uid, String uidFollowedUser) throws InterruptedException, ExecutionException {
		// PART 1: Adds uidFollowedUser to uid's following list
		List<String> followingList = new ArrayList<String>();
		
		// Create a query against the collection
		Query query = Database.FOLLOWS.whereEqualTo("uid", uid);

		// Retrieve query results asynchronously using query.get()
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		if (querySnapshot.get().getDocuments().size() == 1) {
			// Get current following list and add uidFollowedUser
			DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
			followingList = (List<String>) document.get("following");
			followingList.add(uidFollowedUser);
			
			// Update database
			String docId = document.getId();
			DocumentReference docRef = Database.FOLLOWS.document(docId);
			docRef.update("following", followingList);
		}		

		// PART 2: Adds uid to uidFollowedUser's followers list
		List<String> followersList = new ArrayList<String>();
				
		// Create a query against the collection
		query = Database.FOLLOWS.whereEqualTo("uid", uidFollowedUser);

		// Retrieve query results asynchronously using query.get()
		querySnapshot = query.get();

		if (querySnapshot.get().getDocuments().size() == 1) {
			// Get current followers list and add uidFollowedUser
			DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
			followersList = (List<String>) document.get("followers");
			followersList.add(uid);

			// Update database
			String docId = document.getId();
			DocumentReference docRef = Database.FOLLOWS.document(docId);
			docRef.update("followers", followersList);
		}
	}

	// Removes user from following list and from follower list of other user
	@SuppressWarnings("unchecked")
	public static void unfollowUser(String uid, String uidUnfollowedUser) throws InterruptedException, ExecutionException {
		// PART 1: Removes uidUnfollowedUser from uid's following list
		List<String> followingList = new ArrayList<String>();
		
		// Create a query against the collection
		Query query = Database.FOLLOWS.whereEqualTo("uid", uid);

		// Retrieve query results asynchronously using query.get()
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		if (querySnapshot.get().getDocuments().size() == 1) {
			// Get current following list and remove uidFollowedUser
			DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
			followingList = (List<String>) document.get("following");
			for (int i = followingList.size() - 1; i>=0; i--)
				if (followingList.get(i).equals(uidUnfollowedUser))
					followingList.remove(i);

			// Update database
			String docId = document.getId();
			DocumentReference docRef = Database.FOLLOWS.document(docId);
			docRef.update("following", followingList);
		}		

		// PART 2: Removes uid from uidFollowedUser's followers list
		List<String> followersList = new ArrayList<String>();
				
		// Create a query against the collection
		query = Database.FOLLOWS.whereEqualTo("uid", uidUnfollowedUser);

		// Retrieve query results asynchronously using query.get()
		querySnapshot = query.get();

		if (querySnapshot.get().getDocuments().size() == 1) {
			// Get current followers list and remove uidFollowedUser
			DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
			followersList = (List<String>) document.get("followers");
			for (int i = followersList.size() - 1; i>=0; i--)
				if (followersList.get(i).equals(uid))
					followersList.remove(i);

			// Update database
			String docId = document.getId();
			DocumentReference docRef = Database.FOLLOWS.document(docId);
			docRef.update("followers", followersList);
		}
	}
}
