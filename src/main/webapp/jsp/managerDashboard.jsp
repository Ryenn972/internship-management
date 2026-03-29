<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.Application, model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getIdRole() != 3) {
        response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp"); return;
    }
    List<Application> apps = (List<Application>) request.getAttribute("applications");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Responsable — Candidatures</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">🎓 Gestion des stages</a>
    <div>
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp">Dashboard</a>
        <a href="${pageContext.request.contextPath}/offer?action=list">Offres</a>
        <a href="${pageContext.request.contextPath}/manager?action=list">Candidatures</a>
        <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    </div>
</nav>

<div class="container">
    <h2>📂 Candidatures à traiter</h2>

    <% if (apps == null || apps.isEmpty()) { %>
        <p>Aucune candidature à afficher.</p>
    <% } else { %>
    <table>
        <thead>
            <tr>
                <th>Étudiant</th>
                <th>Offre de stage</th>
                <th>Date de candidature</th>
                <th>Statut</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <% for (Application app : apps) { %>
            <tr>
                <td><%= app.getStudentName() %></td>
                <td><%= app.getOfferTitle() %></td>
                <td><%= app.getApplicationDate() %></td>
                <td>
                    <%
                        String s = app.getStatus();
                        String css = "VALIDATED".equals(s) ? "badge-success"
                                   : "REJECTED".equals(s) ? "badge-danger"
                                   : "badge-pending";
                    %>
                    <span class="badge <%= css %>"><%= s %></span>
                </td>
                <td>
                    <% if ("PENDING".equals(app.getStatus())) { %>
                        <a class="btn btn-sm"
                           href="${pageContext.request.contextPath}/manager?action=validate&id=<%= app.getIdApplication() %>">✔ Valider</a>
                        <a class="btn btn-sm btn-danger"
                           href="${pageContext.request.contextPath}/manager?action=reject&id=<%= app.getIdApplication() %>"
                           onclick="return confirm('Refuser cette candidature ?')">✘ Refuser</a>
                    <% } else { %>
                        <span style="color:#888; font-style:italic">Déjà traité</span>
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
