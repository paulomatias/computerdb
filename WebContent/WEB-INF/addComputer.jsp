<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section id="main">

	<h1>Add a Computer</h1>
	
	<form action="/ProjetWebExcilys/AddComputerServlet" method="POST">
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
					<input type="date" name="introducedDate" pattern="YY-MM-dd"/>
					<span class="help-inline">YYYY-MM-dd</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinuedDate" pattern="YY-MM-dd"/>
					<span class="help-inline">YYYY-MM-dd</span>
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
			<input type="submit" value="Add" class="btn primary">
			or <a href="index.jsp" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />