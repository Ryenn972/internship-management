package controller;

import model.User;
import service.ApplicationService;
import service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet dédié au responsable pédagogique (rôle 3).
 * Délègue les actions validate/reject à ApplicationServlet via redirect,
 * et expose une vue filtrée des candidatures.
 */
@WebServlet("/manager")
public class ManagerServlet extends HttpServlet {

    private final ApplicationService  applicationService  = new ApplicationService();
    private final NotificationService notificationService = new NotificationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // Vérification du rôle
        if (user == null || user.getIdRole() != 3) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "list":
                request.setAttribute("applications", applicationService.getAllApplications());
                request.getRequestDispatcher("/jsp/managerDashboard.jsp")
                        .forward(request, response);
                break;

            case "validate":
                int idVal = Integer.parseInt(request.getParameter("id"));
                applicationService.updateStatus(idVal, "VALIDATED");
                int sIdVal = applicationService.getStudentIdByApplication(idVal);
                if (sIdVal != -1) {
                    notificationService.sendNotification(sIdVal,
                            "Votre candidature a été validée par le responsable pédagogique.");
                }
                response.sendRedirect(request.getContextPath() + "/manager?action=list");
                break;

            case "reject":
                int idRej = Integer.parseInt(request.getParameter("id"));
                applicationService.updateStatus(idRej, "REJECTED");
                int sIdRej = applicationService.getStudentIdByApplication(idRej);
                if (sIdRej != -1) {
                    notificationService.sendNotification(sIdRej,
                            "Votre candidature a été refusée par le responsable pédagogique.");
                }
                response.sendRedirect(request.getContextPath() + "/manager?action=list");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/manager?action=list");
        }
    }
}