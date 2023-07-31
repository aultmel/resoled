package org.launchcode.liftoff.shoefinder.models.dto;

import org.launchcode.liftoff.shoefinder.models.UserEntity;

public class ReportDTO {

    private UserEntity userEntity;
    private String complaintDetail;


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
}
