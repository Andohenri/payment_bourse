<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>LOGIN</title>
</head>
<body>
	<h1>LOGIN</h1>
	<h2 style="color: red;"><c:out value='<%= request.getAttribute("error") %>'></c:out></h2>
	<form action="login" method="post">
		<label>Email</label>
		<input type="email" name="email" placeholder="Your email..." required /><br />
		<label>Password</label>
		<input type="password" name="password" placeholder="Your password..." required /><br />
		<button type="submit">LOGIN</button>
	</form>
</body>
</html>