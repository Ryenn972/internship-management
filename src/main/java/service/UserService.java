package service;

import dao.UserDAO;
import model.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public User login(String email, String password) {
        return userDAO.login(email, password);
    }

    public boolean register(User user) {
        if (userDAO.emailExists(user.getEmail())) return false;
        return userDAO.register(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }

    public boolean deleteUser(int idUser) {
        return userDAO.deleteUser(idUser);
    }

    public boolean updateUserRole(int idUser, int idRole) {
        return userDAO.updateUserRole(idUser, idRole);
    }
}