package controller;

import model.Application;
import model.User;
import service.ApplicationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/application")
public class ApplicationServlet extends HttpServlet {

    private ApplicationService applicationService = new ApplicationService();
    private NotificationService notificationService = new NotificationService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("list".equals(action)) {
            request.setAttribute("applications", applicationService.getAllApplications());
            request.getRequestDispatcher("jsp/applications.jsp").forward(request, response);
        }
        else if ("validate".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            applicationService.updateStatus(id, "VALIDATED");

            // Ici il faudrait récupérer le student_id → simplification possible
            // notificationService.sendNotification(studentId, "Your application has been validated");

            response.sendRedirect("application?action=list");
        }
        else if ("reject".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            applicationService.updateStatus(id, "REJECTED");

            // notificationService.sendNotification(studentId, "Your application has been rejected");

            response.sendRedirect("application?action=list");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int offerId = Integer.parseInt(request.getParameter("offerId"));

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Application application = new Application();
        application.setApplicationDate(new Date());
        application.setStudentId(user.getIdUser());
        application.setOfferId(offerId);

        applicationService.apply(application);

        response.sendRedirect("offer?action=list");
    }
}