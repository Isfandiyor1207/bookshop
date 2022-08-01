<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale.message"/>
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
                    <a href="${pageContext.request.contextPath}/pages/registration.jsp" class="signup-image-link"><fmt:message key="label.password.verification"/></a>
                </div>

                <div class="signin-form">
                    <h2 class="form-title"><fmt:message key="label.password.verification"/></h2>
                    <p style="font-size: 12px !important; margin-bottom: 10px !important;">We are send your email verification password please check your email and enter this code.</p>
                    <form action="${pageContext.request.contextPath}/controller" class="register-form" id="login-form">
                        <input type="hidden" name="command" value="password_verification">
                        <div class="form-group">
                            <label><i class="zmdi zmdi-account material-icons-name"></i></label>
                            <input type="text" name="verification_code" placeholder="Verification code" required="required"/>
                        </div>
                        <b><small style="color: red">${verification_password_error}</small></b>

                        <div class="form-group form-button">
                            <input type="submit" name="signin" id="signin" class="form-submit" value="<fmt:message key="label.login"/>" />
                        </div>
                    </form>
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