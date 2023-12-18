<%--
  Created by IntelliJ IDEA.
  User: vniko
  Date: 18/12/2023
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SEI STATO BANNATO</title>
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            background-color: #f2f2f2;
        }

        .message-container {
            text-align: center;
            padding: 20px;
            border: 2px solid #ff0000;
            border-radius: 8px;
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<a href="<%= request.getContextPath() %>/Dispatcher?controllerAction=HomeManager.view">
    <button id="home" class="button">Home</button>
</a>
<div class="message-container">
    <h1>SEI STATO BANNATO</h1>
    <p>Non puoi effettuare ordini. Se pensi ci sia stato un errore, contatta il nostro centro di assistenza inviando una mail a <a href="mailto:administrationmaltomondo@gmail.com">administrationmaltomondo@gmail.com</a>.</p>
</div>
</body>
</html>

