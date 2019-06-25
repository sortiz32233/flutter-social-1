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
    <title>Flutter - Posts</title>
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
                <li class="nav-item active">
                    <a class="nav-link" href="/my-posts">My Posts</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/followers?${uid}">Followers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/following?${uid}">Following</a>
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
    
    <!-- Make New Post -->
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4">Make a new post</h1>
            <h6 style="color:red">${posterror}</h6>
            <p class="lead">Posts by you: ${posts}</p>
            <hr class="my-4">
            <form method="post" action="/submit-post">
                <div class="form-group">
                    <h5>Post Title:</h5>
                    <input class="form-control" type="text" placeholder="Enter post title" name="post-title" value="${title}"><br>
                    <h5>Post Content:</h5>
                    <textarea class="form-control" placeholder="Enter post content" rows="3" name="post-content">${content}</textarea>
                </div>
                <div class="form-group">
                    <button class="btn btn-primary btn-lg" type="submit">Submit</button>
                </div>
            </form>
        </div>
    </div>

    <!-- All User Posts -->
	<div class="container">
        <c:choose>
            <c:when test="${empty postdata}">
                <br><h5 style="text-align:center">${emptymessage}</h5>
            </c:when>
            <c:otherwise>
                <div class="card-columns">
                    <!-- Post Cards -->
                    <c:forEach items="${postdata}" var="pd">
                        <div class="card border-secondary mb-3">
                            <div class="card-header text-primary">
                                <a href="/my-profile">@${username}</a>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">${pd.getTitle()}</h5>
                                <p class="card-text">${pd.getContent()}</p>
                                <p class="card-text">
                                    <small class="text-muted" style="float:left">${pd.getDate()}</small>
                                    <small class="text-muted" style="float:right">${pd.getLikes()} Likes</small>
                                </p><br><br>
                                <button class="btn btn-block btn-outline-primary">Edit Post</button>
                                <button class="btn btn-block btn-outline-danger">Delete Post</button>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
	</div>
</body>
</html>