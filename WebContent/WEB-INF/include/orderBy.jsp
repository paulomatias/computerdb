<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<thead>
	<tr>
		<th><c:if
				test="${message=='Welcome to your computer database !'}">
				<a href="/ProjetWebExcilys/AffichageServlet?orderby=}"
					title="order by name"></a>
			</c:if> <c:out value="Computer Name" /></th>
		<th><a href="/ProjetWebExcilys/AffichageServlet?orderby=}"
			title="order by name"><c:out value="Computer Name" /></a></th>
		<th><a href="/ProjetWebExcilys/" title="order by introduced date"><c:out
					value="Introduced Date" /></a></th>
		<th><a href="/ProjetWebExcilys/"
			title="order by discontinued date"><c:out
					value="Discontinued Date" /></a></th>
		<th><a href="/ProjetWebExcilys/" title="order by company name"><c:out
					value="Company" /></a></th>
		<th></th>
	</tr>
</thead>