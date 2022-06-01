<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.05.2022
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
    <link rel="stylesheet" type="text/css" href="css/signUp.css">
</head>
<body>
<div class="container">

    <form action="controller" style="border:1px solid #ccc" method="post">
        <input type="hidden" name="command" value="add_user">
        <div class="container">
            <h1>Sign Up</h1>
            <p>Please fill in this form to create an account.</p>
            <hr>

            <label for="firstname"><b>First name</b></label>
            <input type="text" placeholder="First name" name="firstname" id="firstname">

            <label for="lastname"><b>Last name</b></label>
            <input type="text" placeholder="Last name" name="lastname" id="lastname">

            <label for="username"><b>Username</b></label>
            <input type="text" placeholder="Username" name="username" id="username">

            <label for="email"><b>Email</b></label>
            <input type="text" placeholder="Enter Email" name="email" id="email" required>

            <label for="password"><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="password" id="password" required>

            <label for="psw-repeat"><b>Repeat Password</b></label>
            <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required>
            <div class="error"><small>${psw_error}</small></div>

            <label for="phoneNumber"><b>Phone number</b></label>
            <input type="text" name="phoneNumber" placeholder="phone_number" id="phoneNumber">

            <label>
                <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me
            </label>

            <p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>

            <div class="clearfix">
                <input type="submit" class="signupbtn">Sign Up
            </div>
        </div>
    </form>
</div>
</body>
</html>
