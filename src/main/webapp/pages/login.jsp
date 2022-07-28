<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en" scope="session" />
<fmt:setBundle basename="prop.message"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up Form by Colorlib</title>

    <!-- Font Icon -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/style.css">
</head>
<body>

<div class="main">
    <!-- Sing in  Form -->
    <section class="sign-in">
        <div class="container">
            <div class="signin-content">
                <div class="signin-image">
                    <figure>
                        <img src="${pageContext.request.contextPath}/pages/img/signin-image.jpg" alt="sing up image">
                    </figure>
                    <a href="${pageContext.request.contextPath}/pages/registration.jsp" class="signup-image-link"><fmt:message key="label.create_account"/></a>
                </div>

                <div class="signin-form">
                    <h2 class="form-title"><fmt:message key="label.sign_in"/></h2>
                    <form action="${pageContext.request.contextPath}/controller" class="register-form" id="login-form">
                        <input type="hidden" name="command" value="login">
                        <div class="form-group">
                            <label for="username"><i class="zmdi zmdi-account material-icons-name"></i></label>
                            <input type="text" name="username" id="username" placeholder="Your Name" required="required"/>
                        </div>
                        <div class="form-group">
                            <label for="password"><i class="zmdi zmdi-lock"></i></label>
                            <input type="password" name="password" id="password" placeholder="Password" required="required"/>
                        </div>
                        <b><small style="color: red">${login_error}</small></b>
                        <b><small style="color: red">${user_error}</small></b>
                        <div class="form-group">
                            <input type="checkbox" name="remember-me" id="remember-me" class="agree-term" />
                            <label for="remember-me" class="label-agree-term"><span><span></span></span><fmt:message key="label.remember_me"/></label>
                        </div>
                        <div class="form-group form-button">
                            <input type="submit" name="signin" id="signin" class="form-submit" value="<fmt:message key="label.login"/>" />
                        </div>
                    </form>
                    <%--					<div class="social-login">--%>
                    <%--						<span class="social-label">Or login with</span>--%>
                    <%--						<ul class="socials">--%>
                    <%--							<li><a href="#"><i class="display-flex-center zmdi zmdi-facebook"></i></a></li>--%>
                    <%--							<li><a href="#"><i class="display-flex-center zmdi zmdi-twitter"></i></a></li>--%>
                    <%--							<li><a href="#"><i class="display-flex-center zmdi zmdi-google"></i></a></li>--%>
                    <%--						</ul>--%>
                    <%--					</div>--%>
                </div>
            </div>
        </div>
    </section>

</div>

<!-- JS -->
<script src="${pageContext.request.contextPath}/pages/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/pages/js/main.js"></script>

</body>
<!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>