package dao;

import model.Application;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    public boolean apply(Application application) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Prevent duplicate applications
            PreparedStatement check = conn.prepareStatement(
                    "SELECT 1 FROM application WHERE student_id=? AND offer_id=?");
            check.setInt(1, application.getStudentId());
            check.setInt(2, application.getOfferId());
            if (check.executeQuery().next()) return false; // already applied

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO application(application_date, status, student_id, offer_id) " +
                            "VALUES(?,?,?,?)");
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

    /** All applications — with student name and offer title joined */
    public List<Application> getAllApplications() {
        List<Application> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql =
                    "SELECT a.*, " +
                            "       CONCAT(u.first_name,' ',u.last_name) AS student_name, " +
                            "       o.title AS offer_title " +
                            "FROM application a " +
                            "JOIN user u ON a.student_id = u.id_user " +
                            "JOIN internship_offer o ON a.offer_id = o.id_offer";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) list.add(mapApplication(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Applications for a specific student */
    public List<Application> getByStudentId(int studentId) {
        List<Application> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql =
                    "SELECT a.*, " +
                            "       CONCAT(u.first_name,' ',u.last_name) AS student_name, " +
                            "       o.title AS offer_title " +
                            "FROM application a " +
                            "JOIN user u ON a.student_id = u.id_user " +
                            "JOIN internship_offer o ON a.offer_id = o.id_offer " +
                            "WHERE a.student_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapApplication(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int idApplication, String status) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE application SET status=? WHERE id_application=?");
            ps.setString(1, status);
            ps.setInt(2, idApplication);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Retrieve the student_id for a given application (used to send notification) */
    public int getStudentIdByApplication(int idApplication) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT student_id FROM application WHERE id_application=?");
            ps.setInt(1, idApplication);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("student_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Application mapApplication(ResultSet rs) throws SQLException {
        Application a = new Application(
                rs.getInt("id_application"),
                rs.getDate("application_date"),
                rs.getString("status"),
                rs.getInt("student_id"),
                rs.getInt("offer_id")
        );
        a.setStudentName(rs.getString("student_name"));
        a.setOfferTitle(rs.getString("offer_title"));
        return a;
    }
}