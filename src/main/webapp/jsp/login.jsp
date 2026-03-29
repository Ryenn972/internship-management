<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>

<h2>Login</h2>

<form action="../login" method="post">
    Email: <input type="text" name="email" required><br>
    Password: <input type="password" name="password" required><br>
    <input type="submit" value="Login">
</form>

<%
    if(request.getParameter("error") != null){
%>
    <p style="color:red;">Invalid email or password</p>
<%
    }
%>

</body>
</html>