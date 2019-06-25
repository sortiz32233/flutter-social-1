<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <title>Flutter - ${displayUsername}'s Followers</title>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="/">Flutter</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/my-profile">My Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/my-posts">My Posts</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/followers?${loggedInUid}">Followers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/following?${loggedInUid}">Following</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/search">Search</a>
                </li>
            </ul>
            <ul class="navbar-nav navbar-right">
                <li class="nav-item"><a class="nav-link" href="/logout">Log Out</a></li>
            </ul>
        </div>
    </nav>

    <!-- Header -->
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4">${displayUsername}</h1>
            <p class="lead">${followerCount} Followers</p>
        </div>
    </div>

    <!-- Follower Table -->
    <div class="container">
        <table class="table" style="text-align:center">
            <thead>
                <tr>
                    <th scope="col">User Id</th>
                    <th scope="col">Username</th>
                    <th scope="col">Profile</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach items="${followerUsers}" var="fu">
	            	<tr>
			       		<td>${fu.getUID()}</td>
			   			<td>${fu.getUsername()}</td>
                        <td>
                            <a class="btn btn-outline-primary btn-sm" href="/profile?${fu.getUID()}">View Profile</a>
                        </td>
		           	</tr>
	           	</c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>