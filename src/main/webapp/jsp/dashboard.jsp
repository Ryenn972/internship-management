<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if(user == null){
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>

<h2>Welcome <%= user.getFirstName() %></h2>

<div class="navbar">
    <a href="../offer?action=list">Offers</a>
    <a href="../application?action=list">Applications</a>
    <a href="../notifications">Notifications</a>
    <a href="../admin?action=list">Admin</a>
    <a href="../logout">Logout</a>
</div>

<div class="container">
    <h2>Dashboard</h2>
    <p>Welcome to the Internship Management System</p>
</div>

</body>
</html>