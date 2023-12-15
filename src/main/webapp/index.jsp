<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="css/styles.css">
<!DOCTYPE html>
<html>
<head>
    <title>MALTOMONDO Shop</title>
</head>
<body class="corpo">
<div id="container" class="container">
    <div id="subcontainer" class="subcontainer">
    <h1 class="logo"><!-- Defining the logo element -->
        MaltoMondo
    </h1>
    </div>
    <a href="<%= request.getContextPath() %>/Dispatcher?controllerAction=HomeManager.view"><button id="button" class="button">Vai al negozio</button></a>
</div>
<%@include file="/include/Footer.inc"%>
</body>
</html>
