<!-- Defining the header section of the page -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>
<%@page import="com.maltomondo.maltomondo.model.mo.Utente"%>
<%
    Boolean loggedOnObj = (Boolean) request.getAttribute("loggedOn");
    boolean loggedOn = (loggedOnObj != null && loggedOnObj.booleanValue());
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Home";
%>
<header>
    <div id="container" class="container">
        <h1 class="logo"><!-- Logo del sito -->
            MaltoMondo
        </h1>
        <link rel="stylesheet" type="text/css" href="css/styles.css">

        <% if (!loggedOn) { %>
        <a href="<%= request.getContextPath() %>/Dispatcher?controllerAction=HomeManager.loginView">
            <button id="login" class="button">Login</button>
        </a>
        <% } if (!loggedOn) {  %>
        <a href="<%= request.getContextPath() %>/Dispatcher?controllerAction=HomeManager.registerView">
            <button id="register" class="button">Registrati</button>
        </a>
        <% } if (loggedOn) { %>
        <a href="<%= request.getContextPath() %>/Dispatcher?controllerAction=HomeManager.logout">
            <button id="logout" class="button">Logout</button>
        </a>
        <% } %>
        <a href="<%= request.getContextPath() %>/Dispatcher?controllerAction=HomeManager.view">
            <button id="home" class="button">Home</button>
        </a>
    </div>
</header>
<!-- OCCHIO AL BOTTONE DEVI METTERCI LOGIN E NON VAI AL NEGOZIO -->