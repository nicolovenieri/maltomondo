<%--
  Created by IntelliJ IDEA.
  User: vniko
  Date: 14/12/2023
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <script>
        function showPassword() {
            var passwordInput = document.getElementById("password");
            var passwordAlert = document.getElementById("passwordAlert");

            if (passwordInput.type === "password") {
                passwordInput.type = "text";
            } else {
                passwordInput.type = "password";
            }

            passwordAlert.style.display = "none";
        }

        function validateForm() {
            var email = document.getElementById("email").value;
            var password = document.getElementById("password").value;
            var passwordAlert = document.getElementById("passwordAlert");

            if (password.trim() === "") {
                passwordAlert.style.display = "block";
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
<a href="<%= request.getContextPath() %>/Dispatcher?controllerAction=HomeManager.view">
    <button id="home" class="button">Home</button>
</a>
<h2>Login</h2>
<form action="Dispatcher?controllerAction=HomeManager.login" method="post" onsubmit="return validateForm();">
    Email: <label for="email"></label><input type="text" name="email" id="email" required><br>
    Password: <label for="password"></label><input type="password" name="password" id="password" required>
    <label>
        <input type="checkbox" onclick="showPassword()">
    </label> Show Password
    <div id="passwordAlert" style="color: red; display: none;">Password non inserita!</div>
    <br>
    <input type="submit" value="Login">
</form>
</body>
</html>