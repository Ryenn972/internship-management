package dao;

import model.User;
import util.DatabaseConnection;
import util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // ── Authentication ────────────────────────────────────────────────────────

    public User login(String email, String plainPassword) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT u.*, r.role_name FROM user u " +
                    "JOIN role r ON u.id_role = r.id_role " +
                    "WHERE u.email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (PasswordUtil.verify(plainPassword, storedHash)) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ── Registration ──────────────────────────────────────────────────────────

    public boolean register(User user) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO user(first_name, last_name, email, password, id_role) " +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, PasswordUtil.hash(user.getPassword()));
            ps.setInt(5, user.getIdRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExists(String email) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT 1 FROM user WHERE email = ?");
            ps.setString(1, email);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── Admin CRUD ────────────────────────────────────────────────────────────

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT u.*, r.role_name FROM user u " +
                    "JOIN role r ON u.id_role = r.id_role";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) users.add(mapUser(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean addUser(User user) {
        return register(user);
    }

    public boolean deleteUser(int idUser) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // transaction

            // 1. Supprimer les notifications de l'utilisateur
            PreparedStatement ps1 = conn.prepareStatement(
                    "DELETE FROM notification WHERE user_id = ?");
            ps1.setInt(1, idUser);
            ps1.executeUpdate();

            // 2. Supprimer les candidatures de l'utilisateur (si étudiant)
            PreparedStatement ps2 = conn.prepareStatement(
                    "DELETE FROM application WHERE student_id = ?");
            ps2.setInt(1, idUser);
            ps2.executeUpdate();

            // 3. Si c'est une entreprise : supprimer les candidatures liées à ses offres
            //    puis supprimer ses offres
            PreparedStatement ps3 = conn.prepareStatement(
                    "DELETE FROM application WHERE offer_id IN " +
                            "(SELECT id_offer FROM internship_offer WHERE company_id = ?)");
            ps3.setInt(1, idUser);
            ps3.executeUpdate();

            PreparedStatement ps4 = conn.prepareStatement(
                    "DELETE FROM internship_offer WHERE company_id = ?");
            ps4.setInt(1, idUser);
            ps4.executeUpdate();

            // 4. Supprimer l'utilisateur lui-même
            PreparedStatement ps5 = conn.prepareStatement(
                    "DELETE FROM user WHERE id_user = ?");
            ps5.setInt(1, idUser);
            boolean deleted = ps5.executeUpdate() > 0;

            conn.commit();
            return deleted;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserRole(int idUser, int idRole) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE user SET id_role=? WHERE id_user=?");
            ps.setInt(1, idRole);
            ps.setInt(2, idUser);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private User mapUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setIdUser(rs.getInt("id_user"));
        u.setFirstName(rs.getString("first_name"));
        u.setLastName(rs.getString("last_name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setIdRole(rs.getInt("id_role"));
        u.setRoleName(rs.getString("role_name"));
        return u;
    }
}