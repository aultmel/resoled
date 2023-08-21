package org.launchcode.liftoff.shoefinder.models.dto;

import jakarta.validation.constraints.NotEmpty;

public class CreateListingDTO {

    @NotEmpty(message = "Brand is required")
    private String brand;

    @NotEmpty(message = "Size is required")
    private String size;

    @NotEmpty(message = "Style is required")
    private String style;

    @NotEmpty(message = "Condition is required")
    private String condition;

    @NotEmpty(message = "Size is required")
    private String gender;

    @NotEmpty(message = "Title is required")
    private String title;

    public CreateListingDTO() {}

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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

}