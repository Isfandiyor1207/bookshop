<%@ page import="epam.project.bookshop.service.GenreService" %>
<%@ page import="epam.project.bookshop.service.impl.GenreServiceImpl" %>
<%@ page import="epam.project.bookshop.service.AuthorService" %>
<%@ page import="epam.project.bookshop.service.impl.AuthorServiceImpl" %>
<%@ page import="epam.project.bookshop.dto.AuthorDto" %>
<%@ page import="epam.project.bookshop.dto.GenreDto" %>
<%@ page import="java.util.List" %>
<%@ page import="epam.project.bookshop.exception.ServiceException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale.message"/>

<%
    GenreService genreService = GenreServiceImpl.getInstance();

    List<GenreDto> genreList = null;

    try {

        genreList = genreService.findAll();
        request.setAttribute("genreList", genreList);

    } catch (ServiceException e) {
        throw new RuntimeException(e);
    }
%>

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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/item.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/profile.dropdown.css">
    <!-- SLIDER REVOLUTION 4.x CSS SETTINGS -->

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap" rel="stylesheet">

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
                     class="img-fluid"/><span><fmt:message key="label.admin"/></span></h3>
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
                <a href="${pageContext.request.contextPath}/pages/admin/book.jsp">
                    <i class="material-icons">aspect_ratio</i><span><fmt:message key="label.books"/></span>
                </a>
            </li>

            <li class="dropdown">
                <a href="${pageContext.request.contextPath}/pages/admin/author.jsp">
                    <i class="material-icons">apps</i><span><fmt:message key="label.author"/></span>
                </a>
            </li>

            <li class="dropdown">
                <a href="${pageContext.request.contextPath}/pages/admin/genre.jsp">
                    <i class="material-icons">equalizer</i><span><fmt:message key="label.genre"/></span>
                </a>
            </li>

            <li class="dropdown">
                <a href="${pageContext.request.contextPath}/pages/admin/user.jsp">
                    <i class="material-icons">extension</i><span><fmt:message key="label.users"/></span>
                </a>
            </li>
            <li class="dropdown">
                <form action="${pageContext.request.contextPath}/controller" style="margin-bottom: 0">
                    <input type="hidden" name="command" value="find_all_orders">
                    <div style="display: flex; justify-content: left; align-items: center">
                        <i class="material-icons" style="margin: 0 10px 0 20px">inventory</i>
                        <button type="submit"
                                style="padding: 10px;background-color: white; border: none;display: flex; align-items: center; justify-content: left; width: 100%;">
                            <span><fmt:message key="label.user.orders"/></span>
                        </button>
                    </div>
                </form>
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

                    <div class="collapse navbar-collapse d-lg-block d-xl-block d-sm-none d-md-none d-none"
                         id="navbarSupportedContent">
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

            <%--      Collapse      --%>
            <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo"
                    style="margin-bottom: 10px"><fmt:message key="label.filter"/>
            </button>
            <div id="demo" class="collapse">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="search_book">
                    <div style="width: 100%">
                        <label style="width: 20%"><fmt:message key="label.book.name"/></label>
                        <div style="width: 80%; display: inline">
                            <input type="text" name="name" value="">
                        </div>
                    </div>
                    <div style="width: 100%">
                        <label style="width: 20%"><fmt:message key="label.author"/></label>
                        <div style="display: inline; width: 80%">
                            <input type="text" name="fio" value="">
                        </div>
                    </div>
                    <div style="width: 100%;">
                        <label style="width: 20% !important;"><fmt:message key="label.genre"/>:</label>
                        <select id="genre_id" name="genre_id" style="padding: 5px 5px; text-transform: capitalize">
                            <option value="0"></option>
                            <c:forEach var="item" items="${genreList}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <small style="color: red">${genre_id_error}</small>
                    </div>
                    <button type="submit" class="btn btn-primary"><fmt:message key="label.search"/></button>
                </form>
            </div>

            <div style="display: flex; justify-content: space-between; align-items: center">
                <div>
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="read_book">
                        <input type="submit" style="align-content: flex-start" class="btn btn-primary"
                               value="<fmt:message key="label.show_all_btn"/>">
                    </form>
                </div>

                <div>
                    <form>
                        <a href="${pageContext.request.contextPath}/pages/admin/book_create.jsp"
                           style="text-transform: none; align-content: flex-end"
                           class="btn btn-success"><fmt:message key="label.create_btn"/></a>
                    </form>
                </div>


            </div>

            <b><small style="color: red; text-transform: none">${deleted_error}</small></b>

            <table class="table table-light table-bordered"
                   style="font-size: small; vertical-align: center; text-align:  center;">
                <thead class="table-primary">
                <tr>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.id"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.book_name"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.book_isbn"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.book_publisher"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.book_publishingYear"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.book_price"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.book_numberOfBooks"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.book_genre"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.book_author"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.book_image_path"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.update_role"/></th>
                    <th style="font-size: small; text-align: center; vertical-align: center" scope="col"><fmt:message
                            key="label.delete_btn"/></th>
                </tr>
                </thead>

                <tbody>
                <c:set var="count" value="0" scope="page" />
                <c:forEach items="${book_list}" var="item">
                    <tr>
                        <td>
                            <c:set var="count" value="${count + 1}" scope="page"/>
                            <c:out value="${count}"/>
                        </td>
                        <td style="text-transform: capitalize"><c:out value="${item.name}"/></td>
                        <td><c:out value="${item.isbn}"/></td>
                        <td style="text-transform: capitalize"><c:out value="${item.publisher}"/></td>
                        <td><c:out value="${item.publishingYear}"/></td>
                        <td><c:out value="${item.price}"/></td>
                        <td><c:out value="${item.numberOfBooks}"/></td>
                        <td style="text-transform: capitalize">
                            <c:forEach items="${item.genreDtoList}" var="genreList">
                                <c:out value="${genreList.name}"/>
                            </c:forEach>
                        </td>
                        <td style="text-transform: capitalize">
                            <c:forEach items="${item.authorDtoList}" var="authorList">
                                <c:out value="${authorList.fio}"/>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${item.attachmentDtoList}" var="imageList">
                                <c:out value="${imageList.uploadPath}${imageList.absoluteName}"/>
                            </c:forEach>
                        </td>
                        <td style="text-transform: none">
                            <form>
                                <input type="hidden" name="command" value="find_book_by_id">
                                <button type="submit" class="btn btn-outline-warning" name="book_id" value="${item.id}">
                                    <fmt:message key="label.update_btn"/></button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="delete_book">
                                    <%--                                <label type="submit"><i class="bi bi-trash"></i></label>--%>
                                <button type="submit" name="delete_by_id" class="btn btn-outline-danger"
                                        value="${item.id}"><fmt:message key="label.delete_btn"/></button>
                            </form>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>


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

    var coll = document.getElementsByClassName("collapsible");
    var i;

    for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function () {
            this.classList.toggle("active");
            var content = this.nextElementSibling;
            if (content.style.display === "block") {
                content.style.display = "none";
            } else {
                content.style.display = "block";
            }
        });
    }

</script>

</body>
</html>

