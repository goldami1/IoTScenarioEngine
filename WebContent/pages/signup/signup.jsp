<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<title>Scenario Engine - Sign up</title>
	<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Karla:400,700|Montserrat:700" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<link rel="stylesheet" href="style.css">
</head>
<body>
		<form action="../../signup" method="post">
				<h1>Scenario Engine</h1>
				<div class="input">
					<label class="input__label">Select account type</label>
					<div class="toggle-set " id="user-type">
						<input type="radio"  class="toggle-set__input" id="switch_left" name="account_type" value="enduser" checked/>
						<label class="toggle-set__label" for="switch_left">User</label>

						<input type="radio" class="toggle-set__input" id="switch_right" name="account_type" value="vendor" />
						<label class="toggle-set__label" for="switch_right">Vendor</label>
					</div>
				</div>

				
				<div id="enduser" >
					<div class="input">
						<label class="input__label">First name</label>
						<input type="text"  placeholder="First name" name="firstname" >
					</div>
					<div class="input">
						<label class="input__label">Last name</label>
						<input type="text"  placeholder="Last name" name="lastname" >	
					</div>	
				</div>				
				<div id="vendor" class="is-hidden">
					<div class="input">
						<label class="input__label">Company name</label>
						<input type="text"  placeholder="Company name" name="companyname" >
					</div>
					<div class="input">
						<label class="input__label">Description</label>
						<input type="text"  placeholder="Description" name="lastname" >
					</div>
					<div class="input--file">
						<label class="input__label">Logo</label>
						<label class="input--file__button full-width" for="file-input" id="file-label">Upload File</label>
						<input type="file" name="logo" id="file-input"  accept="image/*">
					</div>
				</div>

	

				
				<div class="input">
					<label class="input__label">Email</label>
					<input type="text"  placeholder="Email" name="username" >
				</div>
				<div class="input">
					<label class="input__label">Password</label>
					<input type="password"  placeholder="Password" name="password" >
				</div>
				
				<button type="submit" class="input button--blue full-width" >Sign up</button>
				<footer class="text--light text--center">
					<p >have an account ? <a  href="login.html" >Log in now</a></p>
				</footer>
		</form>

<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="script.js"></script>
</body>
</html>