package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public User login(String email, String password) {
        return userDAO.login(email, password);
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

    public boolean updateUserRole(int idUser, int roleId) {
        return userDAO.updateUserRole(idUser, roleId);
    }
}
