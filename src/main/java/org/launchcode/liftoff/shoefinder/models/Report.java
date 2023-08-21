package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;

@Entity
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "reported_user")
    private UserEntity userEntity;
    private String complaintDetail;
    @ManyToOne
    @JoinColumn(name = "submitting_user")
    private UserEntity submittingUser;

    public Report() {
    }

    public Report (UserEntity userEntity, String complaintDetail, UserEntity submittingUser) {
        this.userEntity = userEntity;
        this.complaintDetail = complaintDetail;
        this.submittingUser = submittingUser;
    }


    public long getId() {
        return id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getComplaintDetail() {
        return complaintDetail;
    }

    public void setComplaintDetail(String complaintDetail) {
        this.complaintDetail = complaintDetail;
    }

    public UserEntity getSubmittingUser() {
        return submittingUser;
    }

    public void setSubmittingUser(UserEntity submittingUser) {
        this.submittingUser = submittingUser;
    }
}
