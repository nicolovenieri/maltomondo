
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Page</title>
</head>
<%@include file="/include/Header.jsp"%>
<body>

<h2>Admin Page</h2>

<form action="adminOperations" method="post">
    <label for="email">Inserisci l'indirizzo email dell'utente:</label>
    <input type="text" id="email" name="email" required>

    <button type="submit" name="action" value="block">Blocca Utente</button>
    <button type="submit" name="action" value="delete">Cancella Utente</button>
</form>

</body>
<%@include file="/include/Footer.inc"%>
</html>
