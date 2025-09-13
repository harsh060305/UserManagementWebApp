package com.example.webapp;

import com.example.webapp.UserDAO;
import com.example.webapp.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserDAO dao = new UserDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = dao.authenticateUser(username, password);
        if(user != null){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            if("admin".equals(user.getRole())) response.sendRedirect("admin.jsp");
            else response.sendRedirect("profile.jsp");
        } else response.sendRedirect("error.jsp");
    }
}
