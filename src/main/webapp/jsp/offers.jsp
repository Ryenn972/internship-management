<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, java.util.Set, model.InternshipOffer, model.User" %>
<%
    User user = (User) session.getAttribute("user");
    // Pas de redirection si non connecté — la page est publique
    int role = (user != null) ? user.getIdRole() : 0;
    List<InternshipOffer> offers = (List<InternshipOffer>) request.getAttribute("offers");
    Set<Integer> appliedOfferIds = (Set<Integer>) request.getAttribute("appliedOfferIds");
    if (appliedOfferIds == null) appliedOfferIds = new java.util.HashSet<>();
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Offres de stage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <span class="nav-brand">🎓 Gestion des stages</span>
    <div>
        <% if (user != null) { %>
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp">Dashboard</a>
        <% } %>
        <a href="${pageContext.request.contextPath}/offer?action=list">Offres</a>
        <% if (role == 1) { %>
            <a href="${pageContext.request.contextPath}/application?action=list">Mes candidatures</a>
            <a href="${pageContext.request.contextPath}/notifications">Notifications</a>
        <% } %>
        <% if (role == 2) { %>
            <a href="${pageContext.request.contextPath}/notifications">Notifications</a>
        <% } %>
        <% if (role == 3) { %><a href="${pageContext.request.contextPath}/manager?action=list">Candidatures</a><% } %>
        <% if (role == 4) { %><a href="${pageContext.request.contextPath}/admin?action=list">Utilisateurs</a><% } %>
        <% if (user != null) { %>
            <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
        <% } else { %>
            <a href="${pageContext.request.contextPath}/login">Se connecter</a>
        <% } %>
    </div>
</nav>

<div class="container">
    <div class="page-header">
        <h2>📋 Offres de stage</h2>
        <% if (role == 2) { %>
            <a class="btn" href="${pageContext.request.contextPath}/offer?action=add">+ Nouvelle offre</a>
        <% } %>
    </div>

    <% if (request.getAttribute("success") != null) { %>
        <p class="success"><%= request.getAttribute("success") %></p>
    <% } %>

    <% if (request.getAttribute("info") != null) { %>
        <p class="info"><%= request.getAttribute("info") %></p>
    <% } %>

    <% if (offers == null || offers.isEmpty()) { %>
        <p>Aucune offre disponible pour le moment.</p>
    <% } else { %>
    <table>
        <thead>
            <tr>
                <th>Titre</th>
                <th>Description</th>
                <th>Entreprise</th>
                <th>Date de publication</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <% for (InternshipOffer offer : offers) { %>
            <tr>
                <td><strong><%= offer.getTitle() %></strong></td>
                <td><%= offer.getDescription() %></td>
                <td><%= offer.getCompanyName() != null ? offer.getCompanyName() : "—" %></td>
                <td><%= offer.getPublicationDate() %></td>
                <td>
                    <% if (role == 1) {
                        boolean alreadyApplied = appliedOfferIds.contains(offer.getIdOffer());
                        if (alreadyApplied) { %>
                            <button class="btn btn-sm btn-secondary" disabled>✔ Déjà postulé</button>
                        <% } else { %>
                            <form action="${pageContext.request.contextPath}/application" method="post" style="display:inline">
                                <input type="hidden" name="offerId" value="<%= offer.getIdOffer() %>">
                                <button type="submit" class="btn btn-sm">Postuler</button>
                            </form>
                        <% }
                    } else if (role == 0) { %>
                        <a class="btn btn-sm btn-secondary"
                           href="${pageContext.request.contextPath}/login">Se connecter pour postuler</a>
                    <% } else if (role == 2) { %>
                        <a class="btn btn-sm btn-secondary"
                           href="${pageContext.request.contextPath}/offer?action=edit&id=<%= offer.getIdOffer() %>">Modifier</a>
                        <a class="btn btn-sm btn-danger"
                           href="${pageContext.request.contextPath}/offer?action=delete&id=<%= offer.getIdOffer() %>"
                           onclick="return confirm('Supprimer cette offre ?')">Supprimer</a>
                    <% } %>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div>

</body>
</html>
