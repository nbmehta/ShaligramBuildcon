<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LogIn</title>
<%@include file="admin/includes/include_css_js.jsp"%>
</head>
<body class="loginPage">
	<div id="toppanel">
		<div id="panel">
			<div id="loginLogo">
				<img src="resources/images/header_buildcon.png" />
			</div>
			<div id="homePageLogin">
				<form:form modelAttribute="user" action="j_spring_security_check" method="post" id="loginForm">
					<form:input path="userName" placeholder="Username" />
					<form:password path="password" placeholder="Password" />
					<button type="submit" value="Login" class="submit" id="login">login</button>
<!-- 					
					<div id="rememberMe">
						<input name="remember" type="checkbox" id="remember" />
						<p>
							Remember Me | <a class="iframe" href="forgotPassword.php">Forgot Password</a>
						</p>
					</div>
 -->
					<div class="clear"></div>
				</form:form>
			</div>
		</div>
	</div>
	<c:if test="${param.failed eq 1 }">
		<div class="errors" id="message">
			<div class="messageCentered">
				<ol>
					<li>Username or password is invalid</li>
				</ol>
			</div>
		</div>
	</c:if>
</body>
</html>