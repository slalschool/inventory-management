<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Inventory Management</title>
		
		<style type="text/css">
			<%@ include file="css/styles.css" %>
		</style>
	</head>
	<body>
		<div>
			<h1>Inventory Management</h1>
			
			<div class="header">
				<a href="${pageContext.request.contextPath}/" class="header-button">VIEW ALL</a>
				<a href="${pageContext.request.contextPath}/add" class="header-button">ADD PIZZA</a>
			</div>
		</div>
		<div class="search-functions">
                <form action="search">
                    <label class="search-bar-label">Search Bar:<input type="text" name="search_bar" class="search-bar"/>
                    <input type="submit" value="Search" name="sumbit" class="button"/></label> 
                    <label>
                        <label><input class="radio" type="radio" name="search_attribute" value="ID">ID </label>
                        <label><input class="radio" type="radio" name="search_attribute" value="name">Name </label>
                    </label>
                </form> 
            </div>
		<div>
			<table border="1">
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Pepperoni</th>
					<th>Sausage</th>
					<th>Bacon</th>
					<th>Quantity</th>
					<th>Actions</th>
				</tr>
				<c:forEach var="pizza" items="${pizza}">
					<tr>
						<td><c:out value="${pizza.id}" /></td>
						<td><c:out value="${pizza.name}" /></td>
						<td><c:out value="${pizza.pepperoni}" /></td>
						<td><c:out value="${pizza.sausage}" /></td>
						<td><c:out value="${pizza.bacon}" /></td>
						<td><c:out value="${pizza.quantity}" /></td>
						<td> 
							<div>
								<a href="${pageContext.request.contextPath}/update?action=purchase&id=${pizza.id}" class="button">PURCHASE</a>
								<a href="${pageContext.request.contextPath}/edit?id=${pizza.id}" class="button">EDIT</a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>