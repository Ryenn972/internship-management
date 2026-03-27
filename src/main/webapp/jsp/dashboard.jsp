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
</head>
<body>

<h2>Welcome <%= user.getFirstName() %></h2>

<a href="../logout">Logout</a>

</body>
</html>