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
				<a href="${pageContext.request.contextPath}/add" class="header-button">ADD A PIZZA</a>
			</div>
		</div>
		<div>
			<c:if test="${pizza != null}">
				<h2>Edit Pizza</h2>
				<form action="update" method="post">
					<input type="hidden" name="id" value="<c:out value="${pizza.id}" />" />
					
					<label>Name<input type="text" name="name" value="<c:out value="${pizza.name}" />" /></label>
					<label>Pepperoni<input type="text" name="pepperoni" value="<c:out value="${pizza.pepperoni}" />" /></label>
					<label>Sausage<input type="text" name="sausage" value="<c:out value="${pizza.sausage}" />" /></label>
					<label>Bacon<input type="text" name="bacon" value="<c:out value="${pizza.bacon}" />" /></label>
					<label>
						Quantity
						<select name="quantity">
							<c:forEach begin="1" end="1000" varStatus="loop">
								<option value="${loop.index}" 
									<c:if test="${pizza.quantity == loop.index}">selected</c:if>
								>
									${loop.index}
								</option>
							</c:forEach>
						</select>
					</label>
					<div class="form-actions">
						<input type="submit" value="Save" name="submit" />
						<input type="submit" value="Delete" name="submit" />
					</div>
				</form>
			</c:if>
			<c:if test="${pizza == null}">
				<h2>Add a Pizza</h2>
				<form action="insert" method="post">
					<input type="hidden" name="id" />
					
					<label>Name<input type="text" name="name" /></label>
					<label>Pepperoni<input type="text" name="pepperoni" /></label>
					<label>Sausage<input type="text" name="sausage" /></label>
					<label>Bacon<input type="text" name="bacon" /></label>
					<label>
							Quantity
							<select name="quantity">
								<c:forEach begin="1" end="1000" varStatus="loop">
									<option value="${loop.index}">${loop.index}</option>
								</c:forEach>
							</select>
						</label>
					<input type="submit" value="Add" name="sumbit" />
				</form>
			</c:if>
		</div>
	</body>
</html>