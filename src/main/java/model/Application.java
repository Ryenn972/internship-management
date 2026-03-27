package model;

import java.util.Date;

public class Application {
    private int idApplication;
    private Date applicationDate;
    private String status;
    private int studentId;
    private int offerId;

    public Application() {}

    public Application(int idApplication, Date applicationDate, String status, int studentId, int offerId) {
        this.idApplication = idApplication;
        this.applicationDate = applicationDate;
        this.status = status;
        this.studentId = studentId;
        this.offerId = offerId;
    }

    public int getIdApplication() {
        return idApplication;
    }

    public void setIdApplication(int idApplication) {
        this.idApplication = idApplication;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }
}