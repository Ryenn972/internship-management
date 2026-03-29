package dao;

import model.Application;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    // Ajouter une candidature
    public boolean apply(Application application) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO application(application_date, status, student_id, offer_id) VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, new java.sql.Date(application.getApplicationDate().getTime()));
            ps.setString(2, "PENDING");
            ps.setInt(3, application.getStudentId());
            ps.setInt(4, application.getOfferId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer toutes les candidatures
    public List<Application> getAllApplications() {
        List<Application> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM application";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Application app = new Application(
                        rs.getInt("id_application"),
                        rs.getDate("application_date"),
                        rs.getString("status"),
                        rs.getInt("student_id"),
                        rs.getInt("offer_id")
                );
                list.add(app);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Mettre à jour le statut (VALIDATED / REJECTED)
    public boolean updateStatus(int idApplication, String status) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE application SET status=? WHERE id_application=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, idApplication);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}