<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Scenario Engine - Log in</title>
	<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Karla:400,700|Montserrat:700" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<link rel="stylesheet" href="style.css">
</head>
<body>
		<form action="../../login" method="post" id="log_sign_form" class="center">
				<header>
					<h1>Scenario Engine</h1>
					<p>Welcome back,<br>sign in to continue.</p>
				</header>
				<div class="input">
					<label class="input__label">Email</label>
					<input type="text"  placeholder="Email" name="username" >
				</div>
				<div class="input">
					<label class="input__label">Password</label>
					<input type="password"  placeholder="Password" name="password" >
				</div>
					<button type="submit"  id="button_submit" class="button--blue input">Log in</button>
				<footer>
					<p>need an account ? <a href="signup.html" >Sign up now</a></p>
				</footer>
		</form>
<script src="script.js"></script>
</body>
</html>