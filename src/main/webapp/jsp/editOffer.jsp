<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User, model.InternshipOffer" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getIdRole() != 2) {
        response.sendRedirect(request.getContextPath() + "/offer?action=list"); return;
    }
    InternshipOffer offer = (InternshipOffer) request.getAttribute("offer");
    if (offer == null) {
        response.sendRedirect(request.getContextPath() + "/offer?action=list"); return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier une offre</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <span class="nav-brand">🎓 Gestion des stages</span>
    <div>
        <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp">Dashboard</a>
        <a href="${pageContext.request.contextPath}/offer?action=list">Mes offres</a>
        <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    </div>
</nav>

<div class="container" style="max-width:600px">
    <h2>✏️ Modifier l'offre</h2>

    <form action="${pageContext.request.contextPath}/offer" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= offer.getIdOffer() %>">

        <label>Titre *</label>
        <input type="text" name="title" value="<%= offer.getTitle() %>" required maxlength="150">

        <label>Description</label>
        <textarea name="description" rows="5"><%= offer.getDescription() %></textarea>

        <div style="display:flex; gap:10px; margin-top:10px;">
            <button type="submit" class="btn">Enregistrer</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/offer?action=list">Annuler</a>
        </div>
    </form>
</div>

</body>
</html>
