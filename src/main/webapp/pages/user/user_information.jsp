<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale.message"/>
<jsp:useBean id="user_info" type="epam.project.bookshop.dto.UserDto" scope="request"></jsp:useBean>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title>cms dashboard
    </title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/bootstrap.min.css">
    <!----css3---->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/custom.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/profile.dropdown.css">
    <!-- SLIDER REVOLUTION 4.x CSS SETTINGS -->

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <link href="../css/style.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <%--    <link rel="stylesheet" href="css/user_update.css" type="text/css">--%>
    <!--google material icon-->
    <link href="https://fonts.googleapis.com/css2?family=Material+Icons" rel="stylesheet">
</head>
<body>


<div class="wrapper">


    <div class="body-overlay"></div>


    <!-- Sidebar  -->
    <nav id="sidebar">
        <div class="sidebar-header">
            <h3><img src="${pageContext.request.contextPath}/pages/img/logo.png"
                     class="img-fluid"/><span><%=session.getAttribute("username")%></span></h3>
        </div>
        <ul class="list-unstyled components">
            <li class="active">
                <a href="#" class="dashboard"><i class="material-icons">dashboard</i><span><fmt:message
                        key="label.dashboard"/></span></a>
            </li>

            <div class="small-screen navbar-display">
                <li class="dropdown d-lg-none d-md-block d-xl-none d-sm-block">
                    <a href="#homeSubmenu0" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">
                        <i class="material-icons">notifications</i><span> 4 notification</span></a>
                    <ul class="collapse list-unstyled menu" id="homeSubmenu0">
                        <li>
                            <a href="#">You have 5 new messages</a>
                        </li>
                        <li>
                            <a href="#">You're now friend with Mike</a>
                        </li>
                        <li>
                            <a href="#">Wish Mary on her birthday!</a>
                        </li>
                        <li>
                            <a href="#">5 warnings in Server Console</a>
                        </li>
                    </ul>
                </li>

                <li class="d-lg-none d-md-block d-xl-none d-sm-block">
                    <a href="#"><i class="material-icons">apps</i><span>apps</span></a>
                </li>

                <li class="d-lg-none d-md-block d-xl-none d-sm-block">
                    <a href="#"><i class="material-icons">person</i><span>user</span></a>
                </li>

                <li class="d-lg-none d-md-block d-xl-none d-sm-block">
                    <a href="#"><i class="material-icons">settings</i><span>setting</span></a>
                </li>
            </div>


            <li class="dropdown">
                <form action="${pageContext.request.contextPath}/controller" style="margin-bottom: 0">
                    <input type="hidden" name="command" value="find_user_information">
                    <button type="submit" style="padding: 10px;background-color: white; border: none;display: flex; align-items: center; justify-content: left; width: 100%;">
                        <i class="material-icons" style="margin: 0 10px">aspect_ratio</i><span><fmt:message key="label.user.information"/></span></button>
                </form>
            </li>

            <li class="dropdown">
                <form action="${pageContext.request.contextPath}/controller" style="margin-bottom: 0">
                    <input type="hidden" name="command" value="find_user_orders">
                    <button type="submit" style="padding: 10px;background-color: white; border: none;display: flex; align-items: center; justify-content: left; width: 100%;">
                        <i class="material-icons" style="margin: 0 10px">aspect_ratio</i><span><fmt:message key="label.user.orders"/></span></button>
                </form>
<%--                <a href="${pageContext.request.contextPath}/pages/admin/user.jsp">--%>
<%--                    <i class="material-icons">extension</i><fmt:message key="label.user.orders"/>--%>
<%--                </a>--%>
            </li>

            <li class="dropdown">
                <a href="${pageContext.request.contextPath}/index.jsp">
                    <i class="material-icons">extension</i><fmt:message key="label.main.page"/>
                </a>
            </li>
        </ul>

    </nav>

    <!-- Page Content  -->
    <div id="content">

        <div class="top-navbar">
            <nav class="navbar navbar-expand-lg">
                <div class="container-fluid">

                    <button class="d-inline-block d-lg-none ml-auto more-button" type="button" data-toggle="collapse"
                            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="material-icons">more_vert</span>
                    </button>

                    <div class="profile_page">
                        <ul>
                            <li>
                                <a href="#"><fmt:message key="label.language"/></a>
                                <ul class="language">
                                    <li>
                                        <form action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name = "command" value="change_language">
                                            <input type="submit" name="locale" value="en">
                                        </form>
                                    </li>
                                    <li>
                                        <form action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name = "command" value="change_language">
                                            <input type="submit" name="locale" value="ru">
                                        </form>
                                    </li>
                                    <li>
                                        <form action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name = "command" value="change_language">
                                            <input type="submit" name="locale" value="uz">
                                        </form>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>

                    <div class="collapse navbar-collapse d-lg-block d-xl-block d-sm-none d-md-none d-none" id="navbarSupportedContent">
                        <ul class="nav navbar-nav ml-auto">
                            <li class="nav-item">
                                <form action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden" name="command" value="logout">
                                    <button class="nav-link" href="#">
                                        <span class="material-icons">logout</span>
                                    </button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>


        <div class="main-content">

            <div class="container-fluid">
                <form method="post" autocomplete="on" action="${pageContext.request.contextPath}/controller">
                    <%--                    <input type="hidden" name="command" value="update_user">--%>

                    <div class="container-fluid">
                        <label style="width: 30% !important; position: initial"> <fmt:message key="label.firstname_table"/>: </label>
                        <input type="text" name="firstname" class="textBox" autofocus="autofocus"
                               value="<jsp:getProperty name="user_info" property="firstname"/>"
                               style="width: 50% !important; padding: 5px 5px !important;">
                    </div>

                    <div class="container-fluid">
                        <label style="width: 30% !important; position: initial"> <fmt:message key="label.lastname_table"/>: </label>
                        <input type="text" name="lastname" class="textBox" autofocus="autofocus"
                               value="<jsp:getProperty name="user_info" property="lastname"/>"
                               style="width: 50% !important; padding: 5px 5px !important;">
                    </div>

                    <div class="container-fluid">
                        <label style="width: 30% !important; position: initial"> <fmt:message key="label.username"/>: </label>
                        <input type="text" name="username" class="textBox" autofocus="autofocus"
                               value="<jsp:getProperty name="user_info" property="username"/>"
                               style="width: 50% !important; padding: 5px 5px !important;">
                    </div>

                    <div class="container-fluid">
                        <label style="width: 30% !important; position: initial"> <fmt:message key="label.contact_table"/>: </label>
                        <input type="text" name="contact" class="textBox" autofocus="autofocus"
                               value="<jsp:getProperty name="user_info" property="phoneNumber"/>"
                               style="width: 50% !important; padding: 5px 5px !important;">
                    </div>

                    <div class="container-fluid">
                        <label style="width: 30% !important; position: initial"> <fmt:message key="label.email_table"/>: </label>
                        <input type="text" name="email" class="textBox"
                               autofocus="autofocus" value="<jsp:getProperty name="user_info" property="email"/>"
                               style="width: 50% !important; padding: 5px 5px !important;">
                    </div>

                    <form>
                        <input type="hidden" name="command" value="find_user_by_id">
                        <button class="btn btn-outline-warning" name="user_id" value="${user_info.id}">
                            <fmt:message key="label.update_btn"/></button>
                    </form>

                </form>
            </div>

            <footer class="footer" style="vertical-align: bottom !important;">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            <nav class="d-flex">
                                <ul class="m-0 p-0">
                                    <li>
                                        <a href="#">
                                            Home
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            Company
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            Portfolio
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            Blog
                                        </a>
                                    </li>
                                </ul>
                            </nav>

                        </div>
                        <div class="col-md-6">
                            <p class="copyright d-flex justify-content-end"> &copy 2022 Design by
                                <a href="#">Sultonov Isfandiyor</a>
                            </p>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="${pageContext.request.contextPath}/pages/js/jquery-3.3.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/pages/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/pages/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/pages/js/jquery-3.3.1.min.js"></script>


<script type="text/javascript">
    $(document).ready(function () {
        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').toggleClass('active');
            $('#content').toggleClass('active');
        });

        $('.more-button,.body-overlay').on('click', function () {
            $('#sidebar,.body-overlay').toggleClass('show-nav');
        });

    });
</script>

</body>
</html>


