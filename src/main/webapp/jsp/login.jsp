<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion — Gestion des stages</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="login-box">
    <h2>🎓 Gestion des stages</h2>
    <h3>Connexion</h3>

    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <% if (request.getParameter("registered") != null) { %>
        <p class="success">Inscription réussie ! Vous pouvez vous connecter.</p>
    <% } %>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Email</label>
        <input type="email" name="email" required autofocus>

        <label>Mot de passe</label>
        <input type="password" name="password" required>

        <button type="submit">Se connecter</button>
    </form>

    <p style="margin-top:15px;">
        Pas encore de compte ?
        <a href="${pageContext.request.contextPath}/register">S'inscrire</a>
    </p>
</div>

</body>
</html>
