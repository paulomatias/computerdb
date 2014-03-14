<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main">
	<h1 id="homeTitle">${message}</h1>
	<h2>
		<c:out value="${nbrComputers}" />
		Computer(s) found
	</h2>
	<div id="actions">
		<form action="/ProjetWebExcilys/SelectComputerServlet" method="POST">
			<input type="search" id="searchbox" value="" name=searchComputer
				placeholder="Search computer name"> <input type="search"
				id="searchbox" value="" name=searchCompany
				placeholder="Search company name"> <input type="submit"
				id="searchbox" value="Search" class="btn primary">
		</form>
		<a class="btn success" id="add"
			href="/ProjetWebExcilys/AddComputerServlet">Add Computer</a>
	</div>
	<p></p>
	<table class="computers zebra-striped">
		<thead>
			<tr>
				<th>Computer Name</th>
				<th>Introduced Date</th>
				<th>Discontinued Date</th>
				<th>Company</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listComputers}" var="var">
				<tr>
					<td><a
						href="/ProjetWebExcilys/EditComputerServlet?id=${var.id}" title="edit this computer"><c:out
								value="${var.name}" /></a></td>
					<td><c:out value="${var.introduced}" /></td>
					<td><c:out value="${var.discontinued}" /></td>
					<td><c:out value="${var.company.name} " /></td>
					<td><a class="btn danger" id="delete"
						href="/ProjetWebExcilys/DeleteComputerServlet?id=${var.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</section>
<jsp:include page="include/footer.jsp" />