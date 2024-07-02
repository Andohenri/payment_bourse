<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<title>WELCOME</title>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container py-4">
	<h1>Welcome Back! <%= new Date().toString() %></h1>
	<div>
		<a href="login">Go to login</a><br/>
		<a href="register">Go to Register</a>
	</div>
	<ul>
		<li><a href="tarif">Voir les tarifs</a></li>
		<li><a href="student">Voir les etudiants</a></li>
		<li><a href="payment">Aller au payment</a></li>
	</ul>
</div>
</body>
</html>