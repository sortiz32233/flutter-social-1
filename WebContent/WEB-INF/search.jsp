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
    <title>Flutter - Search</title>
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
                <li class="nav-item">
                    <a class="nav-link" href="/followers?${uid}">Followers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/following?${uid}">Following</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/search">Search</a>
                </li>
            </ul>
            <ul class="navbar-nav navbar-right">
                <li class="nav-item"><a class="nav-link" href="/logout">Log Out</a></li>
            </ul>
        </div>
    </nav>
    
    <!-- Search for users -->
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4">Search for users</h1>
            <hr class="my-4">
            <h6 style="color:red">${searchError}</h6><br>
            <form class="form-inline" method="post" action="/submit-search">
                <select class="custom-select my-1 mr-sm-2" name="search-type">
                    <option selected value="">Search by...</option>
                    <option value="username">Username</option>
                    <option value="uid">User Id</option>
                </select>
                <input class="form-control mr-sm-2" type="search" placeholder="Search..." aria-label="Search" name="search-bar">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </div>
    
    <!-- Search results -->
    <div class="container">
        <c:choose>
            <c:when test="${empty searchResults}">
                <br><h5 style="text-align:center">${emptyMessage}</h5>
            </c:when>
            <c:otherwise>
                <div class="card-columns">
                    <!-- Result Cards -->
                    <c:forEach items="${searchResults}" var="sr">
                        <div class="card" style="width: 18rem;">
                            <div class="card-body">
                                <h5 class="card-title">${sr.getUsername()}</h5>
                                <h6 class="card-subtitle mb-2 text-muted">${sr.getUID()}</h6>
                                <br>
                                <a href="/profile?${sr.getUID()}" class="card-link">View Profile</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>