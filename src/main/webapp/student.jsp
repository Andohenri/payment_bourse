<%@page import="com.django.beans.Student"%>
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
	<h1>ETUDIANTS</h1>
	<div class="row">
		<div class="col-md-8">
			<div class="row">
				<h3 class="mb-4">Liste des étudiants</h3>
				<a href="list-grouped">Afficher les etudiants par niveau ou par institut</a>
			</div>
			<div class="row">
				<div class="col-8">
					<form class="row" action="student" method="get">
						<div class="form-group col-md-8">
							<input class="form-control" type="text" name="keyword" value="${ keyword }" placeholder="Rechercher..." />
						</div>
						<button class="btn btn-primary col-md-4" type="submit">Rechercher</button>
					</form>
				</div>
				<div class="col-4">
					<form class="row" action="student" method="get">
						<div class="form-group col-md-8">
							<select class="form-select" name="filter">
								<option value="all" ${ student.sexe == '' ? 'selected' : '' }>Tous</option>
								<option value="minor" ${ student.sexe == '' ? 'selected' : '' }>Mineurs</option>
								<option value="major" ${ student.sexe == '' ? 'selected' : '' }>Majeurs</option>
							</select>
						</div>
						<button class="btn btn-primary col-md-4" type="submit">Filtrer</button>
					</form>
				</div>
			</div>
			<table class="table table-striped">
				<thead>
				  <tr>
				    <th>#</th>
				    <th>Matricule</th>
				    <th>Nom</th>
				    <th>Institut</th>
				    <th>Niveau</th>
				    <th>Equipement</th>
				    <th>Mois restants</th>
				  </tr>
				 </thead>
				<tbody>
				  <c:forEach items="${ students }" var="stud">
			  	  <tr>
				    <td>${ stud.id }</td>
				    <td>${ stud.matricule }</td>
				    <td>${ stud.names }</td>
				    <td>${ stud.institution }</td>
				    <c:forEach var="level" items="${ levels }">
				    	<c:if test="${ stud.getLevelId() == level.id }">
					    	<td>${ level.description }</td>
				    	</c:if>
				    </c:forEach>
				    <td>${ stud.getEquipementPaid() == 1 ? "Payé" : "Non Payé" }</td>
				    <td>${ stud.getRemainMonth() }</td>
				    <td>
				    	<form action="student" method="get">
				    		<input type="hidden" name="studentId" value="${ stud.id }" />
				    		<button class="btn btn-warning">Editer</button>
				    	</form>
				    </td>
				    <td>
				    	<form action="student?action=delete" method="post">
				    		<input type="hidden" name="id" value="${ stud.id }" />
				    		<button class="btn btn-danger" onclick="return confirm('Etes-vous sur?')">Supprimer</button>
				    	</form>
				    </td>
				  </tr>
				  </c:forEach>
				 </tbody>
			</table>
		</div>
		<div class="col-md-4">
			<h3 class="mb-4"><c:out value="${ not empty student ? \"Editer l'étudiant\" : 'Nouvel étudiant'}" /></h3>
			<form class="forms-sample" action="student" method="post">
				<c:if test="${ not empty student }">
					<input type="hidden" name="id" value="${ student.id }" />
				</c:if>
				<div class="form-group">
					<label>Matricule</label><br/>
					<input class="form-control" type="text" name="matricule" value="${ student.matricule }" placeholder="Le matricule de l'etudiant" required />
				</div>
				<div class="form-group">
					<label>Nom et prenoms</label><br/>
					<input class="form-control" type="text" name="names" value="${ student.names }" placeholder="Le nom et prenom de l'etudiant" required />
				</div>
				<div class="form-group">
					<label>Sexe</label>
					<select class="form-select" name="sexe">
						<option value="M" ${ student.sexe == 'M' ? 'selected' : '' }>Masculin</option>
						<option value="F" ${ student.sexe == 'F' ? 'selected' : '' }>Féminin</option>
					</select>
				</div>
				<div class="form-group">
					<label>Date de naissance</label><br/>
					<input class="form-control" type="date" name="birth_date" value="${ student.birthDate }" placeholder="La date de naissance de l'etudiant" required />
				</div>
				<div class="form-group">
					<label>Institution</label><br/>
					<input class="form-control" type="text" name="institution" value="${ student.institution }" placeholder="L'institution de l'etudiant" required />
				</div>
				<div class="form-group">
					<label>Niveau</label><br />
					<select class="form-select" name="levelId">
						<c:forEach var="level" items="${ levels }">
							<option value="${ level.id }" ${ level.id == student.getLevelId() ? 'selected' : '' }>${ level.description }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Email</label><br/>
					<input class="form-control" type="email" name="email" value="${ student.email }" placeholder="L'email de l'etudiant" required />
				</div>
				
				<div class="mt-4">
					<button class="btn btn-primary mr-4" type="submit">Submit</button>
					<c:if test="${ not empty student }">
						<button class="btn btn-secondary" type="reset" onclick="window.history.back()">Annuler</button>
					</c:if>
				</div>	
			</form>
			<c:if test="${ error != null }">
				<h4 class="alert alert-danger mt-4"><c:out value='<%= request.getAttribute("error") %>'></c:out></h4>
			</c:if>
			<c:if test="${ success != null }">
				<h4 class="alert alert-success mt-4"><c:out value='<%= request.getAttribute("success") %>'></c:out></h4>
			</c:if>
			
			<form action="student?action=send" method="post">
				<input class="form-control" type="number" name="id" placeholder="Rechercher..." />
				<button class="btn btn-primary mt-2" type="submit">Envoyer</button>
			</form>
		</div>
	</div>
</div>
</body>
</html>