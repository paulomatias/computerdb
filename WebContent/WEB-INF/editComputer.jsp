<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section id="main">
	<h1 id="homeTitle">Edit the following computer !</h1>
		<table>
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
						<td><c:out value="${name}"/></td>
						<td><c:out value="${introduced}"/></td>
						<td><c:out value="${discontinued}"/></td>
						<td><c:out value="${companyName} "/></td>
					</tr>
			</tbody>
		</table>
	<form action="/ProjetWebExcilys/EditComputerServlet?id=${computerId}" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="name" />
					<span class="help-inline">Required</span>
				</div>
			</div>	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" name="introducedDate" pattern="YY-dd-MM"/>
					<span class="help-inline">YYYY-dd-MM</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinuedDate" pattern="YY-dd-MM"/>
					<span class="help-inline">YYYY-dd-MM</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company">
						<option value="0">--</option>
						<c:forEach items="${listCompanies}" var="var">
							<option value="${var.id}">${var.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Edit" class="btn primary">
			or <a href="index.jsp" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />
