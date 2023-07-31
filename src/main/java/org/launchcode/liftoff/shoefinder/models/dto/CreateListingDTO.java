package org.launchcode.liftoff.shoefinder.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public class CreateListingDTO {

    @NotEmpty
    private String brand;
    @NotEmpty

    private String size;
    @NotEmpty
    private String style;
    @NotEmpty
    private String condition;

    @NotEmpty
    private String gender;


    public CreateListingDTO() {


    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}