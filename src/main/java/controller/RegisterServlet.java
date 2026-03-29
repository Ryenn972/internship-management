package controller;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String email     = request.getParameter("email");
        String password  = request.getParameter("password");
        String roleStr   = request.getParameter("idRole");

        // Validation basique
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName  == null || lastName.trim().isEmpty()  ||
                email     == null || email.trim().isEmpty()     ||
                password  == null || password.trim().isEmpty()  ||
                roleStr   == null) {
            request.setAttribute("error", "Tous les champs sont obligatoires.");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        int idRole;
        try {
            idRole = Integer.parseInt(roleStr);
            // Seuls les rôles student (1) et company (2) sont autorisés à l'inscription
            if (idRole != 1 && idRole != 2) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Rôle invalide.");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setEmail(email.trim());
        user.setPassword(password); // sera hashé dans UserDAO.register()
        user.setIdRole(idRole);

        boolean success = userService.register(user);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/login?registered=1");
        } else {
            request.setAttribute("error", "Cet email est déjà utilisé.");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
        }
    }
}