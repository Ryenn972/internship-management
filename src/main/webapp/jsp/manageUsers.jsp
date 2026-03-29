<%@ page import="java.util.List, model.User" %>
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>

<h2>User Management</h2>

<h3>Add User</h3>
<form action="../admin" method="post">
    First Name: <input type="text" name="firstName" required><br>
    Last Name: <input type="text" name="lastName" required><br>
    Email: <input type="text" name="email" required><br>
    Password: <input type="text" name="password" required><br>
    Role:
    <select name="roleId">
        <option value="1">Student</option>
        <option value="2">Company</option>
        <option value="3">Manager</option>
        <option value="4">Admin</option>
    </select>
    <button type="submit">Add User</button>
</form>

<h3>Users List</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Email</th>
        <th>Role</th>
        <th>Actions</th>
    </tr>

    <%
        for(User u : users){
    %>
    <tr>
        <td><%= u.getIdUser() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getRoleId() %></td>
        <td>
            <a href="../admin?action=delete&id=<%= u.getIdUser() %>">Delete</a>
            <a href="../admin?action=role&id=<%= u.getIdUser() %>&role=1">Student</a>
            <a href="../admin?action=role&id=<%= u.getIdUser() %>&role=2">Company</a>
            <a href="../admin?action=role&id=<%= u.getIdUser() %>&role=3">Manager</a>
            <a href="../admin?action=role&id=<%= u.getIdUser() %>&role=4">Admin</a>
        </td>
    </tr>
    <%
        }
    %>
</table>