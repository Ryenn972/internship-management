package dao;

import model.Notification;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    // Ajouter notification
    public boolean addNotification(Notification notification) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO notification(message, notification_date, user_id) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, notification.getMessage());
            ps.setDate(2, new java.sql.Date(notification.getNotificationDate().getTime()));
            ps.setInt(3, notification.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer notifications d'un utilisateur
    public List<Notification> getNotificationsByUser(int userId) {
        List<Notification> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM notification WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification(
                        rs.getInt("id_notification"),
                        rs.getString("message"),
                        rs.getDate("notification_date"),
                        rs.getInt("user_id")
                );
                list.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}