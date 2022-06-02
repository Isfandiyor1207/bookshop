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
    <title>Login Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {font-family: Arial, Helvetica, sans-serif;}
        form {border: 3px solid #f1f1f1;}

        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #04AA6D;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            opacity: 0.8;
        }

        .cancelBtn {
            width: auto;
            padding: 10px 18px;
            background-color: #f44336;
        }

        .container {
            padding: 16px;
        }

        span.password {
            float: right;
            padding-top: 16px;
        }

        /* Change styles for span and cancel button on extra small screens */
        @media screen and (max-width: 300px) {
            span.password {
                display: block;
                float: none;
            }
            .cancelBtn {
                width: 100%;
            }
        }
    </style>
</head>
<body>

<h2>Login Form</h2>

<%--<form action="/controller" method="post">--%>

<%--    <div class="container">--%>
<%--        <input type="hidden" name="command" value="login">--%>
<%--        <label><b>Username</b></label>--%>
<%--        <label>--%>
<%--            <input type="text" placeholder="Enter Username" name="username">--%>
<%--        </label>--%>

<%--        <label><b>Password</b></label>--%>
<%--        <label>--%>
<%--            <input type="password" placeholder="Enter Password" name="password">--%>
<%--        </label>--%>

<%--        <b><small style="color: #FF0000FF">${login_error}</small></b>--%>

<%--        <button type="submit">Login</button>--%>
<%--        <label>--%>
<%--            <input type="checkbox" checked="checked" name="remember"> Remember me--%>
<%--        </label>--%>
<%--    </div>--%>

<%--    <div class="container" style="background-color:#f1f1f1">--%>
<%--        <button type="button" class="cancelBtn">Cancel</button>--%>
<%--        <span class="password">Forgot <a href="#">password?</a></span>--%>
<%--    </div>--%>
<%--</form>--%>

</body>
</html>

