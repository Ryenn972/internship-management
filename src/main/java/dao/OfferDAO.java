package dao;

import model.InternshipOffer;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfferDAO {

    // Ajouter une offre
    public boolean addOffer(InternshipOffer offer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO internship_offer(title, description, publication_date, company_id) VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, offer.getTitle());
            ps.setString(2, offer.getDescription());
            ps.setDate(3, new java.sql.Date(offer.getPublicationDate().getTime()));
            ps.setInt(4, offer.getCompanyId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer toutes les offres
    public List<InternshipOffer> getAllOffers() {
        List<InternshipOffer> offers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM internship_offer";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InternshipOffer offer = new InternshipOffer(
                        rs.getInt("id_offer"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("publication_date"),
                        rs.getInt("company_id")
                );
                offers.add(offer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offers;
    }

    // Supprimer une offre
    public boolean deleteOffer(int idOffer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM internship_offer WHERE id_offer=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idOffer);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mettre à jour une offre
    public boolean updateOffer(InternshipOffer offer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE internship_offer SET title=?, description=? WHERE id_offer=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, offer.getTitle());
            ps.setString(2, offer.getDescription());
            ps.setInt(3, offer.getIdOffer());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer une offre par ID
    public InternshipOffer getOfferById(int idOffer) {
        InternshipOffer offer = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM internship_offer WHERE id_offer=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idOffer);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                offer = new InternshipOffer(
                        rs.getInt("id_offer"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("publication_date"),
                        rs.getInt("company_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offer;
    }
}