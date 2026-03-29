<%@ page import="java.util.List, model.Notification" %>
<%
    List<Notification> notifications = (List<Notification>) request.getAttribute("notifications");
%>

<h2>My Notifications</h2>

<table border="1">
    <tr>
        <th>Message</th>
        <th>Date</th>
    </tr>

    <%
        for(Notification n : notifications){
    %>
    <tr>
        <td><%= n.getMessage() %></td>
        <td><%= n.getNotificationDate() %></td>
    </tr>
    <%
        }
    %>
</table>