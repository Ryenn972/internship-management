package controller;

import model.Application;
import model.User;
import service.ApplicationService;
import service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/application")
public class ApplicationServlet extends HttpServlet {

    private final ApplicationService  applicationService  = new ApplicationService();
    private final NotificationService notificationService = new NotificationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            // L'étudiant voit ses propres candidatures ; le responsable voit toutes
            if (user.getIdRole() == 1) {
                request.setAttribute("applications",
                        applicationService.getByStudentId(user.getIdUser()));
            } else {
                request.setAttribute("applications",
                        applicationService.getAllApplications());
            }
            request.getRequestDispatcher("/jsp/applications.jsp").forward(request, response);

        } else if ("validate".equals(action)) {
            // Réservé au responsable pédagogique (rôle 3)
            if (user.getIdRole() != 3) {
                response.sendRedirect(request.getContextPath() + "/application?action=list");
                return;
            }
            int id = Integer.parseInt(request.getParameter("id"));
            applicationService.updateStatus(id, "VALIDATED");

            int studentId = applicationService.getStudentIdByApplication(id);
            if (studentId != -1) {
                notificationService.sendNotification(studentId,
                        "Votre candidature a été validée par le responsable pédagogique.");
            }
            response.sendRedirect(request.getContextPath() + "/application?action=list");

        } else if ("reject".equals(action)) {
            if (user.getIdRole() != 3) {
                response.sendRedirect(request.getContextPath() + "/application?action=list");
                return;
            }
            int id = Integer.parseInt(request.getParameter("id"));
            applicationService.updateStatus(id, "REJECTED");

            int studentId = applicationService.getStudentIdByApplication(id);
            if (studentId != -1) {
                notificationService.sendNotification(studentId,
                        "Votre candidature a été refusée par le responsable pédagogique.");
            }
            response.sendRedirect(request.getContextPath() + "/application?action=list");

        } else {
            response.sendRedirect(request.getContextPath() + "/application?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // Seuls les étudiants peuvent postuler
        if (user == null || user.getIdRole() != 1) {
            response.sendRedirect(request.getContextPath() + "/offer?action=list");
            return;
        }

        int offerId = Integer.parseInt(request.getParameter("offerId"));

        Application application = new Application();
        application.setApplicationDate(new Date());
        application.setStudentId(user.getIdUser());
        application.setOfferId(offerId);

        boolean applied = applicationService.apply(application);

        if (!applied) {
            // Déjà candidaté → message d'info
            request.setAttribute("info", "Vous avez déjà postulé à cette offre.");
            request.setAttribute("offers", new service.OfferService().getAllOffers());
            request.getRequestDispatcher("/jsp/offers.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/offer?action=list");
    }
}