package service;

import dao.ApplicationDAO;
import model.Application;

import java.util.List;

public class ApplicationService {

    private final ApplicationDAO applicationDAO = new ApplicationDAO();

    public boolean apply(Application application) {
        return applicationDAO.apply(application);
    }

    public List<Application> getAllApplications() {
        return applicationDAO.getAllApplications();
    }

    public List<Application> getByStudentId(int studentId) {
        return applicationDAO.getByStudentId(studentId);
    }

    public boolean updateStatus(int idApplication, String status) {
        return applicationDAO.updateStatus(idApplication, status);
    }

    public int getStudentIdByApplication(int idApplication) {
        return applicationDAO.getStudentIdByApplication(idApplication);
    }
}