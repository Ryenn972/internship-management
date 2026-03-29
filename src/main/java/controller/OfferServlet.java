package controller;

import model.InternshipOffer;
import model.User;
import service.OfferService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/offer")
public class OfferServlet extends HttpServlet {

    private final OfferService offerService = new OfferService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "list":
                // Company voit ses propres offres ; autres acteurs voient tout
                if (user.getIdRole() == 2) {
                    request.setAttribute("offers", offerService.getByCompanyId(user.getIdUser()));
                } else {
                    request.setAttribute("offers", offerService.getAllOffers());
                }
                request.getRequestDispatcher("/jsp/offers.jsp").forward(request, response);
                break;

            case "add":
                // Réservé aux entreprises
                if (user.getIdRole() != 2) {
                    response.sendRedirect(request.getContextPath() + "/offer?action=list");
                    return;
                }
                request.getRequestDispatcher("/jsp/addOffer.jsp").forward(request, response);
                break;

            case "edit":
                if (user.getIdRole() != 2) {
                    response.sendRedirect(request.getContextPath() + "/offer?action=list");
                    return;
                }
                int idEdit = Integer.parseInt(request.getParameter("id"));
                InternshipOffer toEdit = offerService.getOfferById(idEdit);
                // Vérifier que l'offre appartient à cette entreprise
                if (toEdit == null || toEdit.getCompanyId() != user.getIdUser()) {
                    response.sendRedirect(request.getContextPath() + "/offer?action=list");
                    return;
                }
                request.setAttribute("offer", toEdit);
                request.getRequestDispatcher("/jsp/editOffer.jsp").forward(request, response);
                break;

            case "delete":
                if (user.getIdRole() != 2) {
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

        // Seules les entreprises peuvent créer/modifier des offres
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