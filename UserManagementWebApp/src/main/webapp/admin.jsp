<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page session="true" %>
<%@ page import="com.example.webapp.User" %>
<%@ page import="java.util.List" %>
<%
User user = (User) session.getAttribute("user");
if(user == null || !"admin".equals(user.getRole())) response.sendRedirect("login.jsp");
List<User> users = (List<User>) request.getAttribute("userList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body class="bg-light">
<div class="container mt-5">
<h2 class="text-center mb-4">Admin Dashboard</h2>
<table class="table table-bordered table-striped">
<tr><th>ID</th><th>Username</th><th>Email</th><th>Role</th><th>Action</th></tr>
<%
for(User u : users){
%>
<tr>
<td><%= u.getId() %></td>
<td><%= u.getUsername() %></td>
<td><%= u.getEmail() %></td>
<td><%= u.getRole() %></td>
<td>
<a href="AdminServlet?action=delete&id=<%= u.getId() %>" class="btn btn-danger btn-sm">Delete</a>
</td>
</tr>
<% } %>
</table>
<a href="LogoutServlet" class="btn btn-secondary">Logout</a>
</div>
</body>
</html>
