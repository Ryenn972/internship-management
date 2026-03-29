<%@ page import="model.InternshipOffer" %>
<%
    InternshipOffer offer = (InternshipOffer) request.getAttribute("offer");
%>

<form action="../offer" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= offer.getIdOffer() %>">
    Title: <input type="text" name="title" value="<%= offer.getTitle() %>" required><br>
    Description: <textarea name="description" required><%= offer.getDescription() %></textarea><br>
    <button type="submit">Update Offer</button>
</form>