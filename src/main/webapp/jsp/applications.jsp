<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.Application, model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<Application> apps = (List<Application>) request.getAttribute("applications");
    int role = user.getIdRole();
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Candidatures</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">🎓 Gestion des stages</a>
    <div>
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp">Dashboard</a>
        <a href="${pageContext.request.contextPath}/offer?action=list">Offres</a>
        <% if (role == 1) { %>
            <a href="${pageContext.request.contextPath}/notifications">Notifications</a>
        <% } %>
        <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    </div>
</nav>

<div class="container">
    <h2>
        <%= role == 1 ? "📁 Mes candidatures" : "📂 Toutes les candidatures" %>
    </h2>

    <% if (apps == null || apps.isEmpty()) { %>
        <p>Aucune candidature à afficher.</p>
    <% } else { %>
    <table>
        <thead>
            <tr>
                <% if (role != 1) { %><th>Étudiant</th><% } %>
                <th>Offre</th>
                <th>Date</th>
                <th>Statut</th>
                <% if (role == 3) { %><th>Actions</th><% } %>
            </tr>
        </thead>
        <tbody>
        <% for (Application app : apps) { %>
            <tr>
                <% if (role != 1) { %>
                    <td><%= app.getStudentName() != null ? app.getStudentName() : app.getStudentId() %></td>
                <% } %>
                <td><%= app.getOfferTitle() != null ? app.getOfferTitle() : app.getOfferId() %></td>
                <td><%= app.getApplicationDate() %></td>
                <td>
                    <%
                        String status = app.getStatus();
                        String cssClass = "VALIDATED".equals(status) ? "badge-success"
                                        : "REJECTED".equals(status) ? "badge-danger"
                                        : "badge-pending";
                    %>
                    <span class="badge <%= cssClass %>"><%= status %></span>
                </td>
                <% if (role == 3) { %>
                <td>
                    <% if ("PENDING".equals(app.getStatus())) { %>
                        <a class="btn btn-sm"
                           href="${pageContext.request.contextPath}/manager?action=validate&id=<%= app.getIdApplication() %>">✔ Valider</a>
                        <a class="btn btn-sm btn-danger"
                           href="${pageContext.request.contextPath}/manager?action=reject&id=<%= app.getIdApplication() %>"
                           onclick="return confirm('Refuser cette candidature ?')">✘ Refuser</a>
                    <% } else { %>
                        <span style="color:#888">Traité</span>
                    <% } %>
                </td>
                <% } %>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div>

</body>
</html>
