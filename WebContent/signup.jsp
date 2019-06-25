<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <title>Flutter - Sign Up</title>
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
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/about.jsp">About</a>
                </li>
            </ul>
            <ul class="navbar-nav navbar-right">
                <li class="nav-item"><a class="nav-link" href="/login.jsp">Log In</a></li>
                <li class="nav-item active"><a class="nav-link" href="/signup.jsp">Sign Up <span class="sr-only">(current)</span></a></li>
            </ul>
        </div>
    </nav>
    
    <!-- Sign Up Form -->
    <div class="container"><br><br><br><br><br>
        <h1>Flutter Sign Up</h1>
        <h6 style="color:red">${error}</h6>
    <form method="post" action="/signup">
        <h5>Email:</h5><input class="form-control" type="email" placeholder="Enter Email" name="signup-email" value="${email}"><br>
        <h5>Username:</h5><input class="form-control" type="text" placeholder="Enter Username" name="signup-username" value="${username}"><br>
        <h5>Password:</h5><input class="form-control" type="password" placeholder="Enter Password" name="signup-pass"><br>
        <h5>Confirm Password:</h5><input class="form-control" type="password" placeholder="Confirm Password" name="signup-confirm-pass"><br>
        <button class="btn btn-info" type="submit">Sign Up</button><br><br><br>
    </form>
</body>
</html>