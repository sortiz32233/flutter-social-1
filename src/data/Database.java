package data;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

public class Database {
	public static final FirestoreOptions FIRESTORE_OPTIONS = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("flutter-social-platform").build();
	public static final Firestore DB = FIRESTORE_OPTIONS.getService();
	public static final CollectionReference USERS = DB.collection("users");
	public static final CollectionReference POSTS = DB.collection("post-data");
	public static final CollectionReference FOLLOWS = DB.collection("follow-data");
}
