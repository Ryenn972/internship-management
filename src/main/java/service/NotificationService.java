package service;

import dao.NotificationDAO;
import model.Notification;

import java.util.Date;
import java.util.List;

public class NotificationService {

    private final NotificationDAO notificationDAO = new NotificationDAO();

    public void sendNotification(int userId, String message) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage(message);
        n.setNotificationDate(new Date());
        notificationDAO.addNotification(n);
    }

    public List<Notification> getUserNotifications(int userId) {
        return notificationDAO.getNotificationsByUser(userId);
    }
}