package controller;

import model.Application;
import model.InternshipOffer;
import model.User;
import service.ApplicationService;
import service.OfferService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/offer")
public class OfferServlet extends HttpServlet {

    private final OfferService        offerService        = new OfferService();
    private final ApplicationService  applicationService  = new ApplicationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "list":
                // Entreprise voit ses propres offres, tous les autres voient tout
                if (user != null && user.getIdRole() == 2) {
                    request.setAttribute("offers", offerService.getByCompanyId(user.getIdUser()));
                } else {
                    request.setAttribute("offers", offerService.getAllOffers());
                }

                // Pour un étudiant connecté : on calcule les offres déjà postulées
                // pour griser le bouton "Postuler"
                if (user != null && user.getIdRole() == 1) {
                    List<Application> myApps = applicationService.getByStudentId(user.getIdUser());
                    Set<Integer> appliedIds = new HashSet<>();
                    for (Application a : myApps) {
                        appliedIds.add(a.getOfferId());
                    }
                    request.setAttribute("appliedOfferIds", appliedIds);
                }

                request.getRequestDispatcher("/jsp/offers.jsp").forward(request, response);
                break;

            case "add":
                if (user == null || user.getIdRole() != 2) {
                    response.sendRedirect(request.getContextPath() + "/offer?action=list");
                    return;
                }
                request.getRequestDispatcher("/jsp/addOffer.jsp").forward(request, response);
                break;

            case "edit":
                if (user == null || user.getIdRole() != 2) {
                    response.sendRedirect(request.getContextPath() + "/offer?action=list");
                    return;
                }
                int idEdit = Integer.parseInt(request.getParameter("id"));
                InternshipOffer toEdit = offerService.getOfferById(idEdit);
                if (toEdit == null || toEdit.getCompanyId() != user.getIdUser()) {
                    response.sendRedirect(request.getContextPath() + "/offer?action=list");
                    return;
                }
                request.setAttribute("offer", toEdit);
                request.getRequestDispatcher("/jsp/editOffer.jsp").forward(request, response);
                break;

            case "delete":
                if (user == null || user.getIdRole() != 2) {
                    response.sendRedirect(request.getContextPath() + "/offer?action=list");
                    return;
                }
                int idDelete = Integer.parseInt(request.getParameter("id"));
                InternshipOffer toDelete = offerService.getOfferById(idDelete);
                if (toDelete != null && toDelete.getCompanyId() == user.getIdUser()) {
                    offerService.deleteOffer(idDelete);
                }
                response.sendRedirect(request.getContextPath() + "/offer?action=list");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/offer?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || user.getIdRole() != 2) {
            response.sendRedirect(request.getContextPath() + "/offer?action=list");
            return;
        }

        String action = request.getParameter("action");
        String title  = request.getParameter("title");
        String desc   = request.getParameter("description");

        if (title == null || title.trim().isEmpty()) {
            request.setAttribute("error", "Le titre est obligatoire.");
            request.getRequestDispatcher(
                    "add".equals(action) ? "/jsp/addOffer.jsp" : "/jsp/editOffer.jsp"
            ).forward(request, response);
            return;
        }

        if ("add".equals(action)) {
            InternshipOffer offer = new InternshipOffer();
            offer.setTitle(title.trim());
            offer.setDescription(desc);
            offer.setPublicationDate(new Date());
            offer.setCompanyId(user.getIdUser());
            offerService.addOffer(offer);

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            InternshipOffer offer = new InternshipOffer();
            offer.setIdOffer(id);
            offer.setTitle(title.trim());
            offer.setDescription(desc);
            offerService.updateOffer(offer);
        }

        response.sendRedirect(request.getContextPath() + "/offer?action=list");
    }
}