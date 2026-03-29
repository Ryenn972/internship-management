package model;

import java.util.Date;

public class Notification {
    private int    idNotification;
    private String message;
    private Date   notificationDate;
    private int    userId;

    public Notification() {}

    public Notification(int idNotification, String message,
                        Date notificationDate, int userId) {
        this.idNotification  = idNotification;
        this.message         = message;
        this.notificationDate = notificationDate;
        this.userId          = userId;
    }

    public int    getIdNotification()                       { return idNotification; }
    public void   setIdNotification(int idNotification)    { this.idNotification = idNotification; }

    public String getMessage()              { return message; }
    public void   setMessage(String message){ this.message = message; }

    public Date   getNotificationDate()                         { return notificationDate; }
    public void   setNotificationDate(Date notificationDate)    { this.notificationDate = notificationDate; }

    public int  getUserId()             { return userId; }
    public void setUserId(int userId)   { this.userId = userId; }
}