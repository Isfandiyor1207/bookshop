<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="uz" scope="session"/>
<fmt:setBundle basename="prop.message"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up Form by Colorlib</title>

    <!-- Font Icon -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/pages/fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/style.css">
</head>
<body>

<div class="main">

    <!-- Sign up form -->
    <section class="signup">
        <div class="container">
            <div class="signup-content">
                <div class="signup-form">
                    <h2 class="form-title"><fmt:message key="label.sign_up"/></h2>

                    <form method="post" action="${pageContext.request.contextPath}/controller" class="register-form"
                          id="register-form">
                        <input type="hidden" name="command" value="add_user">
                        <div class="form-group">
                            <label for="firstname"><i class="zmdi zmdi-account material-icons-name"></i></label>
                            <input type="text" name="firstname" id="firstname" placeholder="Your Firstname" value="<c:if test="${param['firstname'] != null}"><%=request.getParameter("firstname")%></c:if>" required=""/>
                        </div>
                        <div class="form-group">
                            <label for="lastname"><i class="zmdi zmdi-account material-icons-name"></i></label>
                            <input type="text" name="lastname" id="lastname" placeholder="Your Lastname"
                                   value="<c:if test="${param['lastname'] != null}"><%=request.getParameter("lastname")%></c:if>" required=""/>
                        </div>
                        <div class="form-group">
                            <label for="username"><i class="zmdi zmdi-account material-icons-name"></i></label>
                            <input type="text" name="username" id="username" placeholder="Username"
                                   value="<c:if test="${param['username'] != null}"><%=request.getParameter("username")%></c:if>" required=""/>
                        </div>
                        <div><small style="color: red">${username_error}</small></div>
                        <div class="form-group">
                            <label for="email"><i class="zmdi zmdi-email"></i></label>
                            <input type="email" name="email" id="email" placeholder="Your Email"
                                   value="<c:if test="${param['email'] != null}"><%=request.getParameter("email")%></c:if>" required=""/>
                        </div>
                        <!---->
                        <div><small style="color: red">${email_error}</small></div>
                        <div class="form-group">
                            <label for="password"><i class="zmdi zmdi-lock"></i></label>
                            <input type="password" name="password" id="password" placeholder="Password"
                                   value="<c:if test="${param['password'] != null}"><%=request.getParameter("password")%></c:if>" required=""/>
                        </div>
                        <div><small style="color: red">${password_error}</small></div>
                        <div class="form-group">
                            <label for="psw_repeat"><i class="zmdi zmdi-lock-outline"></i></label>
                            <input type="password" name="psw_repeat" id="psw_repeat" placeholder="Repeat your password"
                                   required="" value="<c:if test="${param['psw_repeated_error'] != null}"><%=request.getParameter("psw_repeated_error")%></c:if>"/>
                        </div>
                        <div><small style="color: red">${psw_repeat}</small></div>
                        <div class="form-group">
                            <label for="contact"><i class="zmdi zmdi-lock-outline"></i></label>
                            <input type="text" name="contact" id="contact" placeholder="Contact no"
                                   value="<c:if test="${requestScope['contact'] != null}"><%=request.getParameter("contact")%></c:if>"
                                   required=""/>
                        </div>
                        <div><small style="color: red">${phone_number_error}</small></div>
                        <div class="form-group">
                            <input type="checkbox" name="agree-term" id="agree-term" class="agree-term"/>
                            <label for="agree-term" class="label-agree-term"><span><span></span></span><fmt:message
                                    key="label.statement"/></label>
                        </div>
                        <div class="form-group form-button">
                            <input type="submit" name="add_user" id="add_user" class="form-submit"
                                   value="<fmt:message key="label.register"/>"/>
                        </div>
                    </form>
                </div>
                <div class="signup-image">
                    <figure>
                        <img src="${pageContext.request.contextPath}/pages/img/signup-image.jpg" alt="sing up image">
                    </figure>
                </div>
            </div>
        </div>
    </section>


</div>
<!-- JS -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/pages/js/main.js"></script>

</body>
<!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>