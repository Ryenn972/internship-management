package controller;

import model.InternshipOffer;
import model.User;
import service.OfferService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/offer")
public class OfferServlet extends HttpServlet {

    private OfferService offerService = new OfferService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (action == null) action = "list";

        switch (action) {
            case "list":
                request.setAttribute("offers", offerService.getAllOffers());
                request.getRequestDispatcher("jsp/offers.jsp").forward(request, response);
                break;

            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                offerService.deleteOffer(idDelete);
                response.sendRedirect("offer?action=list");
                break;

            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("offer", offerService.getOfferById(idEdit));
                request.getRequestDispatcher("jsp/editOffer.jsp").forward(request, response);
                break;

            default:
                response.sendRedirect("offer?action=list");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            InternshipOffer offer = new InternshipOffer();
            offer.setTitle(title);
            offer.setDescription(description);
            offer.setPublicationDate(new Date());
            offer.setCompanyId(user.getIdUser());

            offerService.addOffer(offer);
            response.sendRedirect("offer?action=list");
        }

        else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            InternshipOffer offer = new InternshipOffer();
            offer.setIdOffer(id);
            offer.setTitle(title);
            offer.setDescription(description);

            offerService.updateOffer(offer);
            response.sendRedirect("offer?action=list");
        }
    }
}