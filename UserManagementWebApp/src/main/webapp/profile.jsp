<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page session="true" %>
<%@ page import="com.example.webapp.User" %>
<%
User user = (User) session.getAttribute("user");
if(user == null) response.sendRedirect("login.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5 bg-white p-4 shadow rounded text-center">
            <h2 class="mb-3">Welcome, <%= user.getUsername() %>!</h2>
            <p><strong>Email:</strong> <%= user.getEmail() %></p>
            <p><strong>Role:</strong> <%= user.getRole() %></p>
            <a href="LogoutServlet" class="btn btn-danger w-100 mt-3">Logout</a>
        </div>
    </div>
</div>
</body>
</html>
