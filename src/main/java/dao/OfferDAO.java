package dao;

import model.InternshipOffer;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfferDAO {

    public boolean addOffer(InternshipOffer offer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO internship_offer(title, description, publication_date, company_id) " +
                            "VALUES(?,?,?,?)");
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

    public List<InternshipOffer> getAllOffers() {
        List<InternshipOffer> offers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql =
                    "SELECT o.*, CONCAT(u.first_name,' ',u.last_name) AS company_name " +
                            "FROM internship_offer o " +
                            "JOIN user u ON o.company_id = u.id_user";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) offers.add(mapOffer(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offers;
    }

    public List<InternshipOffer> getByCompanyId(int companyId) {
        List<InternshipOffer> offers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT o.*, CONCAT(u.first_name,' ',u.last_name) AS company_name " +
                            "FROM internship_offer o " +
                            "JOIN user u ON o.company_id = u.id_user " +
                            "WHERE o.company_id=?");
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) offers.add(mapOffer(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offers;
    }

    public boolean deleteOffer(int idOffer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM internship_offer WHERE id_offer=?");
            ps.setInt(1, idOffer);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOffer(InternshipOffer offer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE internship_offer SET title=?, description=? WHERE id_offer=?");
            ps.setString(1, offer.getTitle());
            ps.setString(2, offer.getDescription());
            ps.setInt(3, offer.getIdOffer());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public InternshipOffer getOfferById(int idOffer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT o.*, CONCAT(u.first_name,' ',u.last_name) AS company_name " +
                            "FROM internship_offer o " +
                            "JOIN user u ON o.company_id = u.id_user " +
                            "WHERE o.id_offer=?");
            ps.setInt(1, idOffer);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapOffer(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InternshipOffer mapOffer(ResultSet rs) throws SQLException {
        InternshipOffer offer = new InternshipOffer(
                rs.getInt("id_offer"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDate("publication_date"),
                rs.getInt("company_id")
        );
        offer.setCompanyName(rs.getString("company_name"));
        return offer;
    }
}