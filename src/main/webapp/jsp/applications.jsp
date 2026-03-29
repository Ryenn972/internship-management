<%@ page import="java.util.List, model.Application" %>
<%
    List<Application> apps = (List<Application>) request.getAttribute("applications");
%>

<h2>Applications</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>

    <%
        for(Application app : apps){
    %>
    <tr>
        <td><%= app.getIdApplication() %></td>
        <td><%= app.getStatus() %></td>
        <td>
            <a href="application?action=validate&id=<%= app.getIdApplication() %>">Validate</a>
            <a href="application?action=reject&id=<%= app.getIdApplication() %>">Reject</a>
        </td>
    </tr>
    <%
        }
    %>
</table>