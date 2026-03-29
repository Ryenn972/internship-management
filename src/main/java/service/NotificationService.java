package service;

import dao.NotificationDAO;
import model.Notification;

import java.util.Date;
import java.util.List;

public class NotificationService {

    private NotificationDAO notificationDAO = new NotificationDAO();

    public void sendNotification(int userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setNotificationDate(new Date());

        notificationDAO.addNotification(notification);
    }

    public List<Notification> getUserNotifications(int userId) {
        return notificationDAO.getNotificationsByUser(userId);
    }
}