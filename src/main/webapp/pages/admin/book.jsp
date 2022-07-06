<%@ page import="epam.project.bookshop.service.GenreService" %>
<%@ page import="epam.project.bookshop.service.impl.GenreServiceImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="uz"/>
<fmt:setBundle basename="prop.message"/>
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
                <a href="#" class="dashboard"><i class="material-icons">dashboard</i><span><fmt:message key="label.dashboard"/></span></a>
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
                <a href="#pageSubmenu7" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">
                    <i class="material-icons">content_copy</i><span><fmt:message key="label.pages"/></span>
                </a>
                <ul class="collapse list-unstyled menu" id="pageSubmenu7">
                    <li>
                        <a href="#">Page 1</a>
                    </li>
                    <li>
                        <a href="#">Page 2</a>
                    </li>
                    <li>
                        <a href="#">Page 3</a>
                    </li>
                </ul>
            </li>
        </ul>

    </nav>

    <!-- Page Content  -->
    <div id="content">

        <div class="top-navbar">
            <nav class="navbar navbar-expand-lg">
                <div class="container-fluid">

                    <button type="button" id="sidebarCollapse" class="d-xl-block d-lg-block d-md-mone d-none">
                        <span class="material-icons">arrow_back_ios</span>
                    </button>

                    <a class="navbar-brand" href="#"> <fmt:message key="label.dashboard"/> </a>

                    <button class="d-inline-block d-lg-none ml-auto more-button" type="button" data-toggle="collapse"
                            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="material-icons">more_vert</span>
                    </button>

                    <div class="collapse navbar-collapse d-lg-block d-xl-block d-sm-none d-md-none d-none"
                         id="navbarSupportedContent">
                        <ul class="nav navbar-nav ml-auto">
                            <li class="dropdown nav-item active">
                                <a href="#" class="nav-link" data-toggle="dropdown">
                                    <span class="material-icons">notifications</span>
                                    <span class="notification">4</span>
                                </a>
                                <ul class="dropdown-menu">
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
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <span class="material-icons">apps</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <span class="material-icons">person</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <span class="material-icons">settings</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>


        <div class="main-content">

            <div>
                <div style="width: 50% !important; display: grid">
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="read_book">
                        <input type="submit" style="align-content: flex-start" class="btn btn-primary" value="<fmt:message key="label.show_all_btn"/>">
                    </form>
                </div>

                <div style="width: 50% !important; display: grid">
                    <form>
                        <a href="${pageContext.request.contextPath}/pages/admin/book_create.jsp" style="text-transform: none; align-content: flex-end"
                           class="btn btn-success"><fmt:message key="label.create_btn"/></a>
                    </form>
                </div>


            </div>

            <b><small style="color: red">${deleted_error}</small></b>

            <table class="table table-light table-bordered" style="font-size: small">
                <thead class="table-primary">
                <tr>
                    <th scope="col" ><fmt:message key="label.id"/></th>
                    <th scope="col" ><fmt:message key="label.book_name"/></th>
                    <th scope="col" ><fmt:message key="label.book_isbn"/></th>
                    <th scope="col" ><fmt:message key="label.book_publisher"/></th>
                    <th scope="col" ><fmt:message key="label.book_publishingYear"/></th>
                    <%--                    <th scope="col" ><fmt:message key="label.book_attachments"/></th>--%>
                    <th scope="col" ><fmt:message key="label.book_price"/></th>
                    <th scope="col" ><fmt:message key="label.book_numberOfBooks"/></th>
                    <th scope="col" ><fmt:message key="label.book_genre"/></th>
                    <th scope="col" ><fmt:message key="label.book_author"/></th>
                    <th scope="col" ><fmt:message key="label.delete_btn"/></th>
                    <th scope="col" ><fmt:message key="label.update_btn"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${book_list}" var="item">
                    <tr>
                        <td style="text-transform: capitalize"><c:out value="${item.id}"/></td>
                        <td><c:out value="${item.name}"/></td>
                        <td><c:out value="${item.isbn}"/></td>
                        <td><c:out value="${item.publisher}"/></td>
                        <td><c:out value="${item.publishingYear}"/></td>
                        <td><c:out value="${item.price}"/></td>
                        <td><c:out value="${item.numberOfBooks}"/></td>
                        <td><c:out value="${item.genreId}" /></td>
                        <td><c:out value="${item.authorId}"/></td>
<%--                        <td><c:out value="${item.attachmentId}"/></td>--%>
<%--                        <td><c:out value="${item.roleId}"/></td>--%>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="delete_book">
                                    <%--                                <label type="submit"><i class="bi bi-trash"></i></label>--%>
                                <button type="submit" name="delete_by_id" class="btn btn-outline-danger" value="${item.id}"><fmt:message key="label.delete_btn"/></button>
                            </form>
                        </td>
                        <td style="text-transform: none">
                            <form>
                                <input type="hidden" name="command" value="find_book_by_id">
                                <button type="submit" class="btn btn-outline-warning" name="book_id" value="${item.id}"><fmt:message key="label.update_btn"/></button>
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

</script>

</body>
</html>

