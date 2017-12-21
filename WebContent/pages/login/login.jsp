<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Scenario Engine - Log in</title>
	<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Karla:400,700|Montserrat:700" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="pages/login/style.css">
</head>
<body>
		<%  String error = (String)request.getAttribute("error"); %>
		
	
		<form action="${pageContext.request.contextPath}/login" method="post">
				<header>
					<h1>Scenario Engine</h1>
					<p class="text--light">Welcome back,<br>sign in to continue.</p>
				</header>
				<div class="input">
					<label class="input__label">Username</label>
					<input type="text"  placeholder="Username" name="username" >
				</div>
				<div class="input">
					<label class="input__label">Password</label>
					<input type="password"  placeholder="Password" name="password" >
				</div>
					<button type="submit"  class="button--blue input full-width">Log in</button>
				<footer class="text--light text--center">
					<c:if test="${error != null}">
    					<div class="message--error text--center"> ${error} </div>
					</c:if>
					<p>need an account ? <a href="signup.html" >Sign up now</a></p>
				</footer>
		</form>
<script src="pages/login/script.js"></script>
</body>
</html>