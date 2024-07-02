<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<title>PAYMENT</title>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container py-4">
	<h1>PAYMENT</h1>
	<div class="row">
		<div class="col-md-8">
			<div class="row">
				<h3 class="mb-4">Liste des paiements</h3>
				<a href="late-list">Afficher les etudiants retardatataires</a>
			</div>
			<table class="table table-striped">
			 	<thead>
				  <tr>
				    <th>#</th>
				    <th>Matricule</th>
				    <th>Nombre de mois</th>
				    <th>Total payé</th>
				    <th>Date de paiement</th>
				  </tr>
				 </thead>
				<tbody>
				  <c:forEach items="${ payments }" var="pay">
			  	  <tr>
				    <td>${ pay.id }</td>
				    <td>${ pay.matricule }</td>
				    <td>${ pay.getNbrMonth() }</td>
				    <td>${ pay.getTotalAmount() } Ar</td>
				    <td>${ pay.getPaymentDate() }</td>
				    <td>
				    	<form action="print" method="get">
				    		<input type="hidden" name="id" value="${ pay.id }" />
				    		<button class="btn btn-success" onclick="return confirm('Etes-vous sur?')">Imprimer</button>
				    	</form>
				    </td>
				    <td>
				    	<form action="payment?action=delete" method="post">
				    		<input type="hidden" name="id" value="${ pay.id }" />
				    		<button class="btn btn-danger" onclick="return confirm('Etes-vous sur?')">Supprimer</button>
				    	</form>
				    </td>
				  </tr>
				  </c:forEach>
				  </tbody>
			</table>
		</div> 
		<div class="col-md-4">
			<h3><c:out value="${ not empty payment ? 'Editer le payment' : 'Nouvelle payment'}" /></h3>
			<form action="payment" method="post">
				<c:if test="${ not empty payment }">
					<input type="hidden" name="id" value="${ payment.id }" />
				</c:if>
				<div class="form-group">
					<label>Matricule</label><br/>
					<input class="form-control" type="text" name="matricule" value="${ payment.matricule }" placeholder="Le matricule de l'etudiant" required />
				</div>
				<div class="form-group">
					<label>Nombre de mois</label><br/>
					<input class="form-control" type="number" name="nbr_month" value="${ payment.getNbrMonth() }" placeholder="Le nombre de mois du bourse" required />
				</div>
				<div class="mt-4">
					<button class="btn btn-primary mr-4" type="submit">Submit</button>
					<c:if test="${ not empty payment }">
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