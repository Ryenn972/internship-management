<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    int role = user.getIdRole();
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard — Gestion des stages</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">🎓 Gestion des stages</a>
    <div>
        <a href="${pageContext.request.contextPath}/offer?action=list">Offres</a>

        <% if (role == 1) { %>
            <a href="${pageContext.request.contextPath}/application?action=list">Mes candidatures</a>
            <a href="${pageContext.request.contextPath}/notifications">Notifications</a>
        <% } %>

        <% if (role == 3) { %>
            <a href="${pageContext.request.contextPath}/manager?action=list">Candidatures</a>
        <% } %>

        <% if (role == 4) { %>
            <a href="${pageContext.request.contextPath}/admin?action=list">Utilisateurs</a>
        <% } %>

        <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    </div>
</nav>

<div class="container">
    <h2>Bienvenue, <%= user.getFirstName() %> <%= user.getLastName() %> !</h2>

    <div class="role-badge">
        <%
            String roleName = "";
            switch(role) {
                case 1: roleName = "Étudiant"; break;
                case 2: roleName = "Entreprise"; break;
                case 3: roleName = "Responsable pédagogique"; break;
                case 4: roleName = "Administrateur"; break;
            }
        %>
        Rôle : <strong><%= roleName %></strong>
    </div>

    <div class="card-grid">
        <% if (role == 1) { %>
            <div class="card">
                <h3>📋 Offres disponibles</h3>
                <p>Consultez et postulez aux offres de stage.</p>
                <a class="btn" href="${pageContext.request.contextPath}/offer?action=list">Voir les offres</a>
            </div>
            <div class="card">
                <h3>📁 Mes candidatures</h3>
                <p>Suivez l'état de vos candidatures.</p>
                <a class="btn" href="${pageContext.request.contextPath}/application?action=list">Mes candidatures</a>
            </div>
            <div class="card">
                <h3>🔔 Notifications</h3>
                <p>Consultez les réponses à vos candidatures.</p>
                <a class="btn" href="${pageContext.request.contextPath}/notifications">Notifications</a>
            </div>
        <% } else if (role == 2) { %>
            <div class="card">
                <h3>📋 Mes offres</h3>
                <p>Gérez vos offres de stage publiées.</p>
                <a class="btn" href="${pageContext.request.contextPath}/offer?action=list">Gérer les offres</a>
            </div>
            <div class="card">
                <h3>➕ Nouvelle offre</h3>
                <p>Publiez une nouvelle offre de stage.</p>
                <a class="btn" href="${pageContext.request.contextPath}/offer?action=add">Ajouter une offre</a>
            </div>
        <% } else if (role == 3) { %>
            <div class="card">
                <h3>📂 Candidatures à traiter</h3>
                <p>Validez ou refusez les candidatures des étudiants.</p>
                <a class="btn" href="${pageContext.request.contextPath}/manager?action=list">Voir les candidatures</a>
            </div>
            <div class="card">
                <h3>📋 Toutes les offres</h3>
                <p>Consultez l'ensemble des offres publiées.</p>
                <a class="btn" href="${pageContext.request.contextPath}/offer?action=list">Voir les offres</a>
            </div>
        <% } else if (role == 4) { %>
            <div class="card">
                <h3>👥 Gestion des utilisateurs</h3>
                <p>Ajoutez, supprimez et attribuez des rôles aux utilisateurs.</p>
                <a class="btn" href="${pageContext.request.contextPath}/admin?action=list">Gérer les utilisateurs</a>
            </div>
            <div class="card">
                <h3>📋 Toutes les offres</h3>
                <p>Consultez l'ensemble des offres publiées.</p>
                <a class="btn" href="${pageContext.request.contextPath}/offer?action=list">Voir les offres</a>
            </div>
        <% } %>
    </div>
</div>

</body>
</html>
