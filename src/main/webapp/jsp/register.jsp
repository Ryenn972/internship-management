<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Inscription — Gestion des stages</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="login-box">
    <h2>🎓 Gestion des stages</h2>
    <h3>Inscription</h3>

    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <label>Prénom</label>
        <input type="text" name="firstName" required>

        <label>Nom</label>
        <input type="text" name="lastName" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Mot de passe</label>
        <input type="password" name="password" required minlength="6">

        <label>Je suis...</label>
        <select name="idRole">
            <option value="1">Étudiant</option>
            <option value="2">Entreprise</option>
        </select>

        <button type="submit">Créer mon compte</button>
    </form>

    <p style="margin-top:15px;">
        Déjà un compte ? <a href="${pageContext.request.contextPath}/login">Se connecter</a>
    </p>
</div>

</body>
</html>
