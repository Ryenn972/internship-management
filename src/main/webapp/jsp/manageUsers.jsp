<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getIdRole() != 4) {
        response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp"); return;
    }
    List<User> users = (List<User>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des utilisateurs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">🎓 Gestion des stages</a>
    <div>
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp">Dashboard</a>
        <a href="${pageContext.request.contextPath}/offer?action=list">Offres</a>
        <a href="${pageContext.request.contextPath}/admin?action=list">Utilisateurs</a>
        <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    </div>
</nav>

<div class="container">
    <h2>👥 Gestion des utilisateurs</h2>

    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <details style="margin-bottom:20px">
        <summary style="cursor:pointer; font-weight:bold; color:#3498db;">➕ Ajouter un utilisateur</summary>
        <div style="padding:15px; border:1px solid #ddd; border-radius:6px; margin-top:10px;">
            <form action="${pageContext.request.contextPath}/admin" method="post">
                <label>Prénom *</label>
                <input type="text" name="firstName" required>
                <label>Nom</label>
                <input type="text" name="lastName">
                <label>Email *</label>
                <input type="email" name="email" required>
                <label>Mot de passe *</label>
                <input type="password" name="password" required minlength="6">
                <label>Rôle</label>
                <select name="roleId">
                    <option value="1">Étudiant</option>
                    <option value="2">Entreprise</option>
                    <option value="3">Responsable pédagogique</option>
                    <option value="4">Administrateur</option>
                </select>
                <button type="submit" class="btn" style="margin-top:10px">Créer l'utilisateur</button>
            </form>
        </div>
    </details>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Prénom</th>
                <th>Nom</th>
                <th>Email</th>
                <th>Rôle</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <% if (users != null) { for (User u : users) { %>
            <tr>
                <td><%= u.getIdUser() %></td>
                <td><%= u.getFirstName() %></td>
                <td><%= u.getLastName() %></td>
                <td><%= u.getEmail() %></td>
                <td><%= u.getRoleName() != null ? u.getRoleName() : u.getIdRole() %></td>
                <td>
                    <!-- Changer le rôle -->
                    <a class="btn btn-sm btn-secondary"
                       href="${pageContext.request.contextPath}/admin?action=role&id=<%= u.getIdUser() %>&role=1">Étudiant</a>
                    <a class="btn btn-sm btn-secondary"
                       href="${pageContext.request.contextPath}/admin?action=role&id=<%= u.getIdUser() %>&role=2">Entreprise</a>
                    <a class="btn btn-sm btn-secondary"
                       href="${pageContext.request.contextPath}/admin?action=role&id=<%= u.getIdUser() %>&role=3">Manager</a>
                    <a class="btn btn-sm btn-secondary"
                       href="${pageContext.request.contextPath}/admin?action=role&id=<%= u.getIdUser() %>&role=4">Admin</a>
                    <!-- Supprimer -->
                    <% if (u.getIdUser() != user.getIdUser()) { %>
                        <a class="btn btn-sm btn-danger"
                           href="${pageContext.request.contextPath}/admin?action=delete&id=<%= u.getIdUser() %>"
                           onclick="return confirm('Supprimer <%= u.getEmail() %> ?')">✘</a>
                    <% } %>
                </td>
            </tr>
        <% } } %>
        </tbody>
    </table>
</div>

</body>
</html>
