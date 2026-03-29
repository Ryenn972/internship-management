package service;

import dao.ApplicationDAO;
import model.Application;

import java.util.List;

public class ApplicationService {

    private ApplicationDAO applicationDAO = new ApplicationDAO();

    public boolean apply(Application application) {
        return applicationDAO.apply(application);
    }

    public List<Application> getAllApplications() {
        return applicationDAO.getAllApplications();
    }

    public boolean updateStatus(int idApplication, String status) {
        return applicationDAO.updateStatus(idApplication, status);
    }
}