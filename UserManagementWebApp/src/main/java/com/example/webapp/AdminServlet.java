package com.example.webapp;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AdminServlet extends HttpServlet {
    private UserDAO dao = new UserDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if(!"admin".equals(user.getRole())) {
            response.sendRedirect("profile.jsp");
            return;
        }

        String action = request.getParameter("action");
        if("delete".equals(action)){
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteUser(id);
        }

        List<User> users = dao.getAllUsers();
        request.setAttribute("userList", users);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
}
