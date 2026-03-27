package model;

import java.util.Date;

public class InternshipOffer {
    private int idOffer;
    private String title;
    private String description;
    private Date publicationDate;
    private int companyId;

    public InternshipOffer() {}

    public InternshipOffer(int idOffer, String title, String description, Date publicationDate, int companyId) {
        this.idOffer = idOffer;
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.companyId = companyId;
    }

    public int getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(int idOffer) {
        this.idOffer = idOffer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}