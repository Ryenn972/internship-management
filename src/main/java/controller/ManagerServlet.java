package controller;

import model.InternshipOffer;
import model.User;
import service.ApplicationService;
import service.NotificationService;
import service.OfferService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/manager")
public class ManagerServlet extends HttpServlet {

    private final ApplicationService  applicationService  = new ApplicationService();
    private final NotificationService notificationService = new NotificationService();
    private final OfferService        offerService        = new OfferService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

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

                // Notification étudiant
                int sIdVal = applicationService.getStudentIdByApplication(idVal);
                if (sIdVal != -1) {
                    notificationService.sendNotification(sIdVal,
                            "Votre candidature a été validée par le responsable pédagogique.");
                }

                // Notification entreprise (uniquement si validé)
                int offerIdVal = applicationService.getOfferIdByApplication(idVal);
                if (offerIdVal != -1) {
                    InternshipOffer offer = offerService.getOfferById(offerIdVal);
                    if (offer != null) {
                        notificationService.sendNotification(offer.getCompanyId(),
                                "Une candidature pour votre offre \"" + offer.getTitle() +
                                        "\" a été validée par le responsable pédagogique.");
                    }
                }
                response.sendRedirect(request.getContextPath() + "/manager?action=list");
                break;

            case "reject":
                int idRej = Integer.parseInt(request.getParameter("id"));
                applicationService.updateStatus(idRej, "REJECTED");

                // Notification étudiant uniquement (pas l'entreprise en cas de refus)
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