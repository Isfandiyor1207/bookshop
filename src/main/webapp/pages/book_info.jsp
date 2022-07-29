<%@ page import="epam.project.bookshop.service.GenreService" %>
<%@ page import="epam.project.bookshop.service.impl.GenreServiceImpl" %>
<%@ page import="epam.project.bookshop.dto.GenreDto" %>
<%@ page import="epam.project.bookshop.exception.ServiceException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="uz"/>
<fmt:setBundle basename="prop.message"/>


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

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Complete Responsive Online Boot Store Website Design Tutorial</title>

    <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/book.css">

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>

    <!-- custom css file link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/main.css"/>
</head>
<body>
<!-- header section starts  -->

<header class="header">
    <div class="header-1">
        <a href="#" class="logo"> <i class="fas fa-book"></i> bookly </a>

        <form action="" class="search-form">
            <input type="search"
                   name=""
                   placeholder="search here..."
                   id="search-box"/>
            <label for="search-box" class="fas fa-search"></label>
        </form>

        <div class="icons">
            <div id="search-btn" class="fas fa-search"></div>
            <a href="#" class="fas fa-heart"></a>
            <%--            <a href="#" class="fas fa-shopping-cart"></a>--%>
            <%--            <div id="login-btn" class="fas fa-user"></div>--%>
            <a href="${pageContext.request.contextPath}/pages/login.jsp"><span class="fas fa-user"></span></a>
            <form action="${pageContext.request.contextPath}/controller" style="display: inline !important;">
                <input type="hidden" name="command" value="get_access_to_user_profile">
                <button type="submit" style="background-color: white" class=""><i class="fa fa-home"
                                                                                  style="margin-left: 1.5rem;font-size: 2.5rem"></i>
                </button>
            </form>
        </div>

    </div>

    <div class="header-2">
        <nav class="navbar">
            <a href="#home">home</a>
            <form action="${pageContext.request.contextPath}/controller" style="display: inline !important;">
                <input type="hidden" name="command" value="find_all_books_page">
                <button type="submit" class="btn btn-success">Books</button>
            </form>
            <a href="#arrivals">arrivals</a>
            <a href="#reviews">reviews</a>
            <a href="#blogs">blogs</a>
        </nav>
    </div>
</header>

<!-- header section ends -->
<div class="container" style="margin: 15px 0">
    <div id="demo" >
        <form action="${pageContext.request.contextPath}/controller" style="width: 480px; padding: 15px; font-size: 15px">
            <input type="hidden" name="command" value="search_to_main_book_page">
            <div style="display: flex; justify-content: flex-start; align-items: center" class="search">
                <label style="width: 40%">Book name</label>
                <div style="width: 60%; display: inline">
                    <input type="text" name="name" value="" style="border: groove !important;">
                </div>
            </div>
            <div style="display: flex; justify-content: flex-start; align-items: center" class="search">
                <label style="width: 40%">Author name</label>
                <div style="display: inline; width: 60%">
                    <input type="text" name="fio" value="" style="border: groove !important;">
                </div>
            </div>
            <div style="display: flex; justify-content: flex-start; align-items: center">
                <label style="width: 40% !important;">Genre:</label>
                <select id="genre_id" name="genre_id" style="padding: 5px 5px; text-transform: capitalize; border: groove">
                    <option value="0"></option>
                    <c:forEach var="item" items="${genreList}">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
                <small style="color: red">${genre_id_error}</small>
            </div>
            <button type="submit" class="btn btn-primary">Search</button>
        </form>
    </div>
</div>
<%--<button type="button" class="btn btn-info collapsible" style="margin-bottom: 10px">Filter</button>--%>

<section class="container" style="padding-top: 0 !important;">
    <c:forEach items="${book_list}" var="bookItem">
        <div class="card">
            <div class="card-image">
                <div class="image-item">
                    <c:forEach items="${bookItem.attachmentDtoList}" var="imgList">
                        <img src="${pageContext.request.contextPath}${imgList.uploadPath}${imgList.absoluteName}">
                    </c:forEach>
                </div>

            </div>
            <div class="card-content">
                <h2>${bookItem.name}</h2>
                <p>$${bookItem.price}</p>
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="find_one_book_information">
                    <button type="submit" class="btn" name="book_id" value="${bookItem.id}">Buy</button>
                </form>
            </div>
        </div>
    </c:forEach>
</section>
<!-- footer section starts  -->

<section class="footer">
    <div class="box-container">
        <div class="box">
            <h3>our locations</h3>
            <a href="#"> <i class="fas fa-map-marker-alt"></i> india </a>
            <a href="#"> <i class="fas fa-map-marker-alt"></i> USA </a>
            <a href="#"> <i class="fas fa-map-marker-alt"></i> russia </a>
            <a href="#"> <i class="fas fa-map-marker-alt"></i> france </a>
            <a href="#"> <i class="fas fa-map-marker-alt"></i> japan </a>
            <a href="#"> <i class="fas fa-map-marker-alt"></i> africa </a>
        </div>

        <div class="box">
            <h3>quick links</h3>
            <a href="#"> <i class="fas fa-arrow-right"></i> home </a>
            <a href="#"> <i class="fas fa-arrow-right"></i> featured </a>
            <a href="#"> <i class="fas fa-arrow-right"></i> arrivals </a>
            <a href="#"> <i class="fas fa-arrow-right"></i> reviews </a>
            <a href="#"> <i class="fas fa-arrow-right"></i> blogs </a>
        </div>

        <div class="box">
            <h3>extra links</h3>
            <a href="#"> <i class="fas fa-arrow-right"></i> account info </a>
            <a href="#"> <i class="fas fa-arrow-right"></i> ordered items </a>
            <a href="#"> <i class="fas fa-arrow-right"></i> privacy policy </a>
            <a href="#"> <i class="fas fa-arrow-right"></i> payment method </a>
            <a href="#"> <i class="fas fa-arrow-right"></i> our serivces </a>
        </div>

        <div class="box">
            <h3>contact info</h3>
            <a href="#"> <i class="fas fa-phone"></i> +123-456-7890 </a>
            <a href="#"> <i class="fas fa-phone"></i> +111-222-3333 </a>
            <a href="#"> <i class="fas fa-envelope"></i> shaikhanas@gmail.com </a>
            <img src="${pageContext.request.contextPath}/pages/img/book_img/worldmap.png" class="map" alt=""/>
        </div>
    </div>

    <div class="share">
        <a href="#" class="fab fa-facebook-f"></a>
        <a href="#" class="fab fa-twitter"></a>
        <a href="#" class="fab fa-instagram"></a>
        <a href="#" class="fab fa-linkedin"></a>
        <a href="#" class="fab fa-pinterest"></a>
    </div>

    <div class="credit">
        created by <span>mr. web designer</span> | all rights reserved!
    </div>
</section>

<!-- footer section ends -->

<!-- loader  -->

<!-- <div class="loader-container">
  <img src="image/book_img/loader-img.gif" alt="" />
</div> -->

<script>
    var coll = document.getElementsByClassName("collapsible");
    var i;

    for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function() {
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

<script src="https://unpkg.com/swiper@7/swiper-bundle.min.js"></script>

<!-- custom js file link  -->
<script src="${pageContext.request.contextPath}/pages/js/main.js"></script>
</body>
</html>
