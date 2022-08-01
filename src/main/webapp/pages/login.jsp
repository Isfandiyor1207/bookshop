<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale.message"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up Form by Colorlib</title>

    <!-- Font Icon -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/style.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js" integrity="sha512-6PM0qYu5KExuNcKt5bURAoT6KCThUmHRewN3zUFNaoI6Di7XJPTMoT6K0nsagZKk2OB4L7E3q1uQKHNHd4stIQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

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
                            <input type="text" name="username" id="username" placeholder="Username" required="required"/>
                        </div>
                        <div class="form-group">
                            <label for="password"><i class="zmdi zmdi-lock"></i></label>
                            <input type="password" name="password" id="password" placeholder="Password" required="required"/>
                        </div>
                        <b><small style="color: red">${login_error}</small></b>
                        <b><small style="color: red">${user_error}</small></b>
                        <div><b><small style="color: #1abc9c">${verification_success}</small></b></div>
                        <a href="${pageContext.request.contextPath}/pages/username_verification.jsp" style="text-decoration: none">
                            <span style="color: #0d6efd;"><fmt:message key="label.msg.forget.password"/></span>
                        </a>
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