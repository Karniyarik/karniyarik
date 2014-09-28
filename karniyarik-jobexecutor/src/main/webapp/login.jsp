<%@ page session="true"%>
<%@page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Karniyarik Job Executor</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body onload='document.loginForm.j_username.focus();'>
<div class="container">
  <%@ include file="header.jsp" %>
  <div class="span-24 rndb">
	<form id="loginForm" name="loginForm" action="j_spring_security_check"
		method="post">
		<c:if test="${not empty param.authfailed}">
			<span id="infomessage" class="errormessage"> Login failed due to: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.</span>
		</c:if> 
		<c:if test="${not empty param.authfailed}">
			<span id="infomessage" class="errormessage"> Login failed due to: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.</span>
		</c:if> 
		<c:if test="${not empty param.newpassword}">
			<span id="infomessage" class="errormessage"> Login failed due to: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />. </span>
		</c:if> 
		<c:if test="${not empty param.acclocked}">
			<span id="infomessage" class="errormessage"> Login failed due to: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />. </span>
		</c:if> 
		<c:if test="${not empty param.accdisabled}">
		<span id="infomessage" class="errormessage"> Login failed due to: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />. </span>
		</c:if> 
		<c:if test="${not empty param.loggedout}">
			<span id="infomessage" class="successmessage"> You have been successfully logged out. </span>
		</c:if>
		<table>
			<tr>
				<td>Username</td>
				<td><input id="usernameField" type="text" name="j_username"
					value="<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input id="passwordField" type="password" name="j_password" /></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Login" /></td>
			</tr>
		</table>
	</form>
	<%@ include file="footer.jsp"%></div>
</div>
</body>
</html>