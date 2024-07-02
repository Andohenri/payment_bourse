<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>REGISTER</title>
</head>
<body>
	<h1>REGISTER</h1>
	<h2 style="color: red;"><c:out value='<%= request.getAttribute("error") %>'></c:out></h2>
	<form action="register" method="post">
		<label>Username</label>
		<input type="text" name="username" placeholder="Your username..." required /><br />
		<label>Email</label>
		<input type="email" name="email" placeholder="Your email..." required /><br />
		<label>Password</label>
		<input type="password" name="password" placeholder="Your password..." required /><br />
		<button type="submit">Register</button>
	</form>
</body>
</html>