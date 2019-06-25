package accounts;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import data.Database;

public class Emails {
	private static final String verificationURL = "https://flutter-social-platform.appspot.com/verify?";
	private static final String verificationServiceEmail = "signup@flutter-social-platform.appspotmail.com";
	
	// Checks if email already exists in the database
	public static boolean emailIsUsed(String email) throws InterruptedException, ExecutionException {
		// Create a query against the users collection
		Query query = Database.USERS.whereEqualTo("email", email);
		// Retrieve query results asynchronously using query.get()
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
			
		if (querySnapshot.get().getDocuments().size() > 0)
			return true;
		return false;
	}
	
	// Sends verification link to user email
	public static String sendVerificationLink(String email, String username, String verificationCode) {
		String link = verificationURL + verificationCode;
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(verificationServiceEmail, "Flutter Registration"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email, username));
			msg.setSubject("Verify Your Flutter Account");
			msg.setText("Hi " + username + ", \n\nPlease verify your email by clicking this link: " + link + "\n\nBest Regards,\nFlutter Team");
			Transport.send(msg);
			
			return "success";
		} catch (AddressException e) {
			return "Error sending email: Invalid address.";
		} catch (MessagingException e) {
			return "Error sending email: MessagingException";
		} catch (UnsupportedEncodingException e) {
			return "Error sending email: UnsupportedEncodingException";
		}
	}
	
	// Generate 24 character alphanumeric String
	public static String generateVerificationCode() {
		StringBuilder randLink = new StringBuilder("");
		for (int i = 0; i < 24; i++) {
			double rand = Math.random()*3;
			if (rand < 1) {
				randLink.append((int) (Math.random()*10));
			} else if (rand < 2){
				randLink.append((char) (Math.random()*26 + 97));
			} else {
				randLink.append((char) (Math.random()*26 + 65));
			}
		}

		return randLink.toString();
	}
}
