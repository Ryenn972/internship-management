package service;

import dao.OfferDAO;
import model.InternshipOffer;

import java.util.List;

public class OfferService {
    private OfferDAO offerDAO = new OfferDAO();

    public boolean addOffer(InternshipOffer offer) {
        return offerDAO.addOffer(offer);
    }

    public List<InternshipOffer> getAllOffers() {
        return offerDAO.getAllOffers();
    }

    public boolean deleteOffer(int idOffer) {
        return offerDAO.deleteOffer(idOffer);
    }

    public boolean updateOffer(InternshipOffer offer) {
        return offerDAO.updateOffer(offer);
    }

    public InternshipOffer getOfferById(int idOffer) {
        return offerDAO.getOfferById(idOffer);
    }
}