<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<title>RETARDATAIRES</title>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container py-4">
	<div class="row">
		<div class="col-md-8">
			<h1>Listes des étudiants retardataires par niveau</h1>
			<div class="row">
				<div class="col-md-6">
					<form action="late-list" method="get">
						<input class="form-control" type="hidden" name="send" value="1" placeholder="Rechercher..." />
						<button class="btn btn-primary mt-2" type="submit">Envoyer les rappels le payments</button>
					</form>
				</div>
				<div class="col-md-6">
					<c:if test="${ error != null }">
						<h4 class="alert alert-danger mt-4"><c:out value='<%= request.getAttribute("error") %>'></c:out></h4>
					</c:if>
					<c:if test="${ success != null }">
						<h4 class="alert alert-success mt-4"><c:out value='<%= request.getAttribute("success") %>'></c:out></h4>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<form class="row" action="late-list" method="get">
				<div class="form-group col-md-6">
					<select class="form-select" name="month">
						<c:forEach var="month" items="${ months }" varStatus="status">
							<option value="${ status.index + 1 }" >${ month }</option>
						</c:forEach>
					</select>
				</div>
				<button class="btn btn-primary col-md-4" type="submit">VOIR</button>
			</form>
		</div>
	</div>
	<c:forEach var="entry" items="${ lateStudentByLevel }">
		<h3 class="border-bottom mt-2">${ entry.key }</h3>
		<table class="table table-striped">
			<thead>
			  <tr>
			    <th>Matricule</th>
			    <th>Nom</th>
			    <th>Sexe</th>
			    <th>Institut</th>
			    <th>Niveau</th>
			    <th>Mois restants</th>
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
			    <td>${ student.getLevelId() }</td>
			  </c:forEach>
			 </tbody>
		</table>
	</c:forEach>
</div>
</body>
</html>