<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<title>ETUDIANTS</title>
</head>
<body>
<%@ include file="/header.jsp" %>
	<div class="container py-4">
		<div class="row">
			<div class="col-md-8">
				<h1>Listes des étudiants par niveau</h1>
			</div>
			<div class="col-md-4">
				<form class="row" action="list-grouped" method="get">
					<div class="form-group col-md-6">
						<select class="form-select" name="institution">
							<option value="" >Par défaut</option>
							<c:forEach var="institut" items="${ institution }">
								<option value="${ institut }" >${ institut }</option>
							</c:forEach>
						</select>
					</div>
					<button class="btn btn-primary col-md-2" type="submit">Filtrer</button>
				</form>
			</div>
		</div>
		<c:forEach var="entry" items="${ studentByLevel }">
			<h3 class="border-bottom mt-2">${ entry.key }</h3>
			<table class="table table-striped">
				<thead>
				  <tr>
				    <th>Matricule</th>
				    <th>Nom</th>
				    <th>Sexe</th>
				    <th>Institut</th>
				    <th>Niveau</th>
				  </tr>
				 </thead>
				<tbody>
				  <c:forEach items="${ entry.value }" var="student">
			  	  <tr>
				    <td>${ student.matricule }</td>
				    <td>${ student.names }</td>
				    <td>${ student.sexe }</td>
				    <td>${ student.institution }</td>
				    <td>${ student.desc }</td>
				  </c:forEach>
				 </tbody>
			</table>
		</c:forEach>
	</div>
</body>
</html>