package controller;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "list":
                request.setAttribute("users", userService.getAllUsers());
                request.getRequestDispatcher("jsp/manageUsers.jsp").forward(request, response);
                break;

            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                userService.deleteUser(idDelete);
                response.sendRedirect("admin?action=list");
                break;

            case "role":
                int idUser = Integer.parseInt(request.getParameter("id"));
                int roleId = Integer.parseInt(request.getParameter("role"));
                userService.updateUserRole(idUser, roleId);
                response.sendRedirect("admin?action=list");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoleId(roleId);

        userService.addUser(user);

        response.sendRedirect("admin?action=list");
    }
}