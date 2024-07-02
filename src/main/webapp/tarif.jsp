<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<title>TARIF</title>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container py-4">
	<h1>TARIFS</h1>
	<div class="row">
		<div class="col-md-8">
			<h3>Liste des tarif</h3>
			<table class="table table-striped">
				<thead>
				  <tr>
				    <th>#</th>
				    <th>Niveau</th>
				    <th>Montant</th>
				  </tr>
				 </thead>
				<tbody>
				  <c:forEach items="${ tarifs }" var="tar">
			  	  <tr>
				    <td>${ tar.id }</td>
				    <c:forEach var="level" items="${ levels }">
				    	<c:if test="${ tar.getLevelId() == level.id }">
					    	<td>${ level.description }</td>
				    	</c:if>
				    </c:forEach>
				    <td>${ tar.amount } Ar</td>
				    <td>
				    	<form action="tarif" method="get">
				    		<input type="hidden" name="tarifId" value="${ tar.id }" />
				    		<button class="btn btn-warning">Editer</button>
				    	</form>
				    </td>
				    <td>
				    	<form action="tarif?action=delete" method="post">
				    		<input type="hidden" name="id" value="${ tar.id }" />
				    		<button class="btn btn-danger" onclick="return confirm('Etes-vous sur?')">Supprimer</button>
				    	</form>
				    </td>
				  </tr>
				  </c:forEach>
				 </tbody>
			</table>
		</div>
		<div class="col-md-4">
			<h3><c:out value="${ not empty tarif ? 'Editer le tarif' : 'Nouvelle tarif'}" /></h3>
			<form action="tarif" method="post">
				<c:if test="${ not empty tarif }">
					<input type="hidden" name="id" value="${ tarif.id }" />
				</c:if>
				<div class="form-group">
					<label>Niveau</label><br />
					<select class="form-control" name="levelId">
						<c:forEach var="level" items="${ levels }">
							<option value="${ level.id }" ${ level.id == tarif.getLevelId() ? 'selected' : '' }>${ level.description }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Montant</label><br/>
					<input class="form-control" type="number" name="amount" value="${ tarif.amount }" placeholder="Le montant du bourse par mois" required />
				</div>
				<div class="form-group">
					<label>Dater de disponibilté</label><br/>
					<input class="form-control" type="date" name="dispo_date" value="${ tarif.disponibilityDate }"  required />
				</div>
				<div class="mt-4">
					<button class="btn btn-primary mr-4" type="submit">Submit</button>
					<c:if test="${ not empty tarif }">
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
		</div>
	</div>
</div>
</body>
</html>