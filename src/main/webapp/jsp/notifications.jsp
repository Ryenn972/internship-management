<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.Notification, model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<Notification> notifications = (List<Notification>) request.getAttribute("notifications");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Notifications</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="nav-brand">🎓 Gestion des stages</a>
    <div>
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp">Dashboard</a>
        <a href="${pageContext.request.contextPath}/offer?action=list">Offres</a>
        <a href="${pageContext.request.contextPath}/application?action=list">Mes candidatures</a>
        <a href="${pageContext.request.contextPath}/notifications">Notifications</a>
        <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    </div>
</nav>

<div class="container">
    <h2>🔔 Mes notifications</h2>

    <% if (notifications == null || notifications.isEmpty()) { %>
        <p>Aucune notification pour le moment.</p>
    <% } else { %>
    <table>
        <thead>
            <tr>
                <th>Message</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
        <% for (Notification n : notifications) { %>
            <tr>
                <td><%= n.getMessage() %></td>
                <td><%= n.getNotificationDate() %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div>

</body>
</html>
