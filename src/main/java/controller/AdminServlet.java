package controller;

import model.User;
import service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || user.getIdRole() != 4) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "list":
                request.setAttribute("users", userService.getAllUsers());
                request.getRequestDispatcher("/jsp/manageUsers.jsp").forward(request, response);
                break;

            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                // Empêcher de supprimer son propre compte
                if (idDelete != user.getIdUser()) {
                    userService.deleteUser(idDelete);
                }
                response.sendRedirect(request.getContextPath() + "/admin?action=list");
                break;

            case "role":
                int idUser = Integer.parseInt(request.getParameter("id"));
                int roleId = Integer.parseInt(request.getParameter("role"));
                userService.updateUserRole(idUser, roleId);
                response.sendRedirect(request.getContextPath() + "/admin?action=list");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/admin?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || user.getIdRole() != 4) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String email     = request.getParameter("email");
        String password  = request.getParameter("password");
        int    roleId    = Integer.parseInt(request.getParameter("roleId"));

        if (firstName == null || email == null || password == null ||
                firstName.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Tous les champs sont obligatoires.");
            request.setAttribute("users", userService.getAllUsers());
            request.getRequestDispatcher("/jsp/manageUsers.jsp").forward(request, response);
            return;
        }

        User newUser = new User();
        newUser.setFirstName(firstName.trim());
        newUser.setLastName(lastName != null ? lastName.trim() : "");
        newUser.setEmail(email.trim());
        newUser.setPassword(password); // hashé dans UserDAO.register()
        newUser.setIdRole(roleId);

        boolean ok = userService.addUser(newUser);
        if (!ok) {
            request.setAttribute("error", "Cet email est déjà utilisé.");
            request.setAttribute("users", userService.getAllUsers());
            request.getRequestDispatcher("/jsp/manageUsers.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin?action=list");
    }
}