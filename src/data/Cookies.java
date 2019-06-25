package data;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cookies {
	// Checks if user is logged in via cookies
	public static boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) {
	    Cookie[] cookies = request.getCookies();
	    int loginCheck = 0;
	    
	    if (cookies == null) {
	    	return false;
	    } else if (cookies.length == 3)
	    	for (Cookie c : cookies) {
	    		if (c.getName().equals("uid"))
	    			loginCheck++;
	    		if (c.getName().equals("username"))
	    			loginCheck++;
	    		if (c.getName().equals("email"))
	    			loginCheck++;
	    	}
	    
	    if (loginCheck == 3)
	    	return true;
	    else {
	    	clearCookies(request, response);
	    	return false;
	    }
	}
	
	// Clears all Flutter cookies from the browser
	public static void clearCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
	    	for (Cookie c : cookies) {
	    		c.setMaxAge(0);
	            response.addCookie(c);
	    	}
	    }
	}
	
	// Returns login cookie data in an array in the order: email, uid, username
	public static String[] getCookieData(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String[] orderedResult = new String[3];

		for (Cookie c : cookies) {
			if (c.getName().equals("email"))
    			orderedResult[0] = c.getValue();
    		if (c.getName().equals("uid"))
    			orderedResult[1] = c.getValue();
    		if (c.getName().equals("username"))
    			orderedResult[2] = c.getValue();
    	}

		return orderedResult;
	}
}
