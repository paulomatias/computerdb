<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${currentPage != 1}">
	<c:if test="${message=='Welcome to your computer database !'}">
		<td><a
			href="/ProjetWebExcilys/DashboardServlet?page=${currentPage - 1}">Previous</a></td>
	</c:if>
	<c:if test="${message=='Computer(s) selected successfully !'}">
		<td><a
			href="/ProjetWebExcilys/SelectComputerServlet?searchComputer=${searchComputer}&searchCompany=${searchCompany}&page=${currentPage - 1}">Previous</a></td>
	</c:if>
	<c:if test="${message=='Computer added successfully !'}">
		<td><a
			href="/ProjetWebExcilys/DashboardServlet?page=${currentPage - 1}">Previous</a></td>
	</c:if>
	<c:if test="${message=='Computer edited successfully !'}">
		<td><a
			href="/ProjetWebExcilys/DashboardServlet?page=${currentPage - 1}">Previous</a></td>
	</c:if>
	<c:if test="${message=='Computer deleted successfully !'}">
		<td><a
			href="/ProjetWebExcilys/DashboardServlet?page=${currentPage - 1}">Previous</a></td>
	</c:if>
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
				<td><a
					href="/ProjetWebExcilys/SelectComputerServlet?searchComputer=${searchComputer}&searchCompany=${searchCompany}&page=${i}">${i}</a></td>
			</c:if>
			<c:if test="${message=='Computer added successfully !'}">
				<td><a href="/ProjetWebExcilys/DashboardServlet?page=${i}">${i}</a></td>
			</c:if>
				<c:if test="${message=='Computer edited successfully !'}">
				<td><a href="/ProjetWebExcilys/DashboardServlet?page=${i}">${i}</a></td>
			</c:if>
			<c:if test="${message=='Computer deleted successfully !'}">
				<td><a href="/ProjetWebExcilys/DashboardServlet?page=${i}">${i}</a></td>
			</c:if>
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:if test="${currentPage lt nbrOfPages}">
			<c:if test="${message=='Welcome to your computer database !'}">
				<td><a href="/ProjetWebExcilys/DashboardServlet?page=${currentPage + 1}">Next</a></td>
			</c:if>
			<c:if test="${message=='Computer(s) selected successfully !'}">
				<td><a
					href="/ProjetWebExcilys/SelectComputerServlet?searchComputer=${searchComputer}&searchCompany=${searchCompany}&page=${currentPage + 1}">Next</a></td>
			</c:if>
			<c:if test="${message=='Computer added successfully !'}">
				<td><a href="/ProjetWebExcilys/DashboardServlet?page=${currentPage + 1}">Next</a></td>
			</c:if>
			<c:if test="${message=='Computer edited successfully !'}">
				<td><a href="/ProjetWebExcilys/DashboardServlet?page=${currentPage + 1}">Next</a></td>
			</c:if>
			<c:if test="${message=='Computer deleted successfully !'}">
				<td><a href="/ProjetWebExcilys/DashboardServlet?page=${currentPage + 1}">Next</a></td>
			</c:if>
</c:if>
