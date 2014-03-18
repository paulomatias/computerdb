<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib uri="/WEB-INF/tags" prefix="tags"%> --%>

<section id="main">
	<h1 id="homeTitle">${message}</h1>
	<h2>
		<c:out value="${nbrComputers}" />
		Computer(s) found
	</h2>
	<div id="actions">
		<form action="/ProjetWebExcilys/SelectComputerServlet" method="GET">
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
						href="/ProjetWebExcilys/EditComputerServlet?id=${var.id}"
						title="edit this computer"><c:out value="${var.name}" /></a></td>
					<td><c:out value="${var.introduced}" /></td>
					<td><c:out value="${var.discontinued}" /></td>
					<td><c:out value="${var.company.name} " /></td>
					<td><a class="btn danger" id="delete"
						href="/ProjetWebExcilys/DeleteComputerServlet?id=${var.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${currentPage != 1}">
		<td><a
			href="/ProjetWebExcilys/DashboardServlet?page=${currentPage - 1}">Previous</a></td>
	</c:if>
	<c:forEach begin="1" end="${nbrOfPages}" var="i">
		<c:choose>
			<c:when test="${currentPage eq i}">
				<td>${i}</td>
			</c:when>
			<c:otherwise>
			<c:if test="${message=='Welcome to your computer database !'}">
				<td><a href="/ProjetWebExcilys/DashboardServlet?page=${i}">${i}</a></td>
			</c:if>
			<c:if test="${message=='Computer(s) selected successfully !'}">
				<td><a href="/ProjetWebExcilys/SelectComputerServlet?searchComputer=${computerName}&searchCompany=${computerCompanyName}&page=${i}">${i}</a></td>
			</c:if>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${currentPage lt nbrOfPages}">
		<td><a
			href="/ProjetWebExcilys/DashboardServlet?page=${currentPage + 1}">Next</a></td>
	</c:if>
	


</section>
<jsp:include page="include/footer.jsp" />
