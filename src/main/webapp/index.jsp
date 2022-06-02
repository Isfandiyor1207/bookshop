<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

    <div class="flex">
        <h2>Login Form</h2>
        <form action="controller">

            <div class="container">
                <input type="hidden" name="command" value="login">
                <label><b>Username</b></label>
                <label>
                    <input type="text" placeholder="Enter Username" name="username">
                </label>

                <label><b>Password</b></label>
                <label>
                    <input type="password" placeholder="Enter Password" name="password">
                </label>

                <b><small style="color: #FF0000FF">${login_error}</small></b>

                <button type="submit">Login</button>
                <label>
                    <input type="checkbox" checked="checked" name="remember"> Remember me
                </label>
            </div>

            <div class="container" style="background-color:#f1f1f1">
                <div>
                    <span><a href="signUp.jsp">Sign Up</a></span>
                    <span class="password">Forgot <a href="#">password?</a></span>
                </div>
            </div>
        </form>
    </div>

</body>
</html>