
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Page</title>
</head>
<%@include file="/include/Header.jsp"%>
<body>

<h2>Admin Page</h2>

<form action="Dispatcher?controllerAction=AdminManagment.blockuser" method="post">
    <label for="emailblock">Inserisci l'indirizzo email dell'utente:</label>
    <input type="text" id="emailblock" name="emailblock" required>
    <button type="submit" name="action" value="block">Blocca Utente</button>
</form>

<form action="Dispatcher?controllerAction=AdminManagment.deleteuser" method="post">
    <label for="emaildelete">Inserisci l'indirizzo email dell'utente:</label>
    <input type="text" id="emaildelete" name="emaildelete" required>
    <button type="submit" name="action" value="block">Cancella Utente</button>
</form>
<!-- FORM PER SETTARE ADMIN QUALCUNO -->
<form action="Dispatcher?controllerAction=AdminManagment.setadmin" method="post">
    <label for="emailadmin">Inserisci l'indirizzo email dell'utente:</label>
    <input type="text" id="emailadmin" name="emailadmin" required>
    <button type="submit" name="action" value="setadmin">Rendi Admin</button>
</form>
<!--FORM PER RIMUOVERE ADMIN QUALCUNO -->
<form action="Dispatcher?controllerAction=AdminManagment.unsetadmin" method="post">
    <label for="emaildowngrade">Inserisci l'indirizzo email dell'utente:</label>
    <input type="text" id="emaildowngrade" name="emaildowngrade" required>
    <button type="submit" name="action" value="unsetadmin">Togli Admin</button>
</form>
<!--FORM PER SBLOCCARE QUALCUNO -->
<form action="Dispatcher?controllerAction=AdminManagment.unblock" method="post">
    <label for="emailunblock">Inserisci l'indirizzo email dell'utente:</label>
    <input type="text" id="emailunblock" name="emailunblock" required>
    <button type="submit" name="action" value="unblock">Sblocca</button>
</form>

</body>
<%@include file="/include/Footer.inc"%>
</html>
