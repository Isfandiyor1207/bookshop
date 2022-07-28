<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.message"/>
<jsp:useBean id="book_info" scope="request" type="epam.project.bookshop.dto.BookDto"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Complete Responsive Online Boot Store Website Design Tutorial</title>

    <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/book_order.css">

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>

    <!-- custom css file link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/main.css"/>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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

<div class="flex-box">
    <div class="left">
        <div class="big-img">
            <c:forEach var="image" items="${book_info.attachmentDtoList}">
                <img src="${pageContext.request.contextPath}${image.uploadPath}${image.absoluteName}" width="100%">
            </c:forEach>
        </div>
    </div>
    <div class="right">
        <h1 class="content-title">
            <jsp:getProperty name="book_info" property="name"/>
            <small>by</small>
            <c:forEach var="author" items="${book_info.authorDtoList}">
                <output>${author.fio}</output>
            </c:forEach>
        </h1>
        <p>
            <jsp:getProperty name="book_info" property="description"/>
        </p>
        <h2>
            Rate:
            <jsp:getProperty name="book_info" property="averageRate"/>
            Voted:
            <jsp:getProperty name="book_info" property="numberOfVotedUser"/>
        </h2>
        <h1 class="price">$
            <jsp:getProperty name="book_info" property="price"/>
        </h1>
        <div class="rate-comment">
            <form action="${pageContext.request.contextPath}/controller" class="rate-wrapper">
                <input type="hidden" name="command" value="rate_book"/>
                <div class="rate">
                    <input type="radio" id="star5" name="rate" value="5"/>
                    <label for="star5" title="text"></label>
                    <input type="radio" id="star4" name="rate" value="4"/>
                    <label for="star4" title="text"></label>
                    <input type="radio" id="star3" name="rate" value="3"/>
                    <label for="star3" title="text"></label>
                    <input type="radio" id="star2" name="rate" value="2"/>
                    <label for="star2" title="text"></label>
                    <input type="radio" id="star1" name="rate" value="1"/>
                    <label for="star1" title="text"></label>
                </div>
                <button type="submit" name="book_id" value="${book_info.id}" class="btn btn-primary">Rate</button>
            </form>
        </div>

        <div class="quantity">
            <div class="order">
                <form action="${pageContext.request.contextPath}/controller">
                    <div class="order-quantity">
                        <label>Quantity: </label>
                        <input type="number" name="quantity" value="1">
                        <small style="color: red;">${book_quantity_error}</small>
                    </div>
                    <div class="order-btn">
                        <input type="hidden" name="command" value="check_user_to_authorization">
                        <button class="btn btn-success" type="submit" name="book_id" value="${book_info.id}">Buy
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

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

<script src="https://unpkg.com/swiper@7/swiper-bundle.min.js"></script>

<!-- custom js file link  -->
<script src="${pageContext.request.contextPath}/pages/js/main.js"></script>
</body>
</html>