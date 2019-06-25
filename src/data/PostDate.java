package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostDate {
	// Gets current Date.toString
	public static String getCurrentDate() {
		Date currentDate = new Date();
		return currentDate.toString();
	}
	
	// Converts the default Date.toString to a Date object
	public static String convertToLocal(String dateString) throws ParseException {
		// Converts dateString to Date object
		SimpleDateFormat convertToDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));
		Date date = convertToDate.parse(dateString);
		
		// Converts Date object to display format
		SimpleDateFormat displayDate = new SimpleDateFormat("EEE MM/dd/yyyy hh:mm aaa");
		return displayDate.format(date);
	}
}
