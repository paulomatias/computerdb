<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section id="main">
	<h1 id="homeTitle">456 Computers found (TODO : compter le nombre de computer)</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<a class="btn success" id="add" href="addComputer.jsp">Add Computer</a>
	</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<th>Computer Name</th>
					<th>Introduced Date</th>
					<th>Discontinued Date</th>
					<th>Company</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><a href="#">Precision 3500</a></td>
					<td>2010-05-07</td>
					<td>2012-06-01</td>
					<td>Dell</td>
				</tr>
				<tr>
					<td><a href="#">Macbook Air</a></td>
					<td>2005-05-09</td>
					<td>2008-06-06</td>
					<td>Apple</td>
				</tr>
			<c:forEach items="${listComputers}" var="var">
				<tr>
					<td><c:out value="${var.name}"/></td>
					<td><c:out value="${var.introduced}"/></td>
					<td><c:out value="${var.discontinued}"/></td>
					<td><c:out value="${var.company.id} "/></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
</section>

<jsp:include page="include/footer.jsp" />
