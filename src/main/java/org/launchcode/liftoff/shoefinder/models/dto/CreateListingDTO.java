package org.launchcode.liftoff.shoefinder.models.dto;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import org.launchcode.liftoff.shoefinder.models.*;

import java.util.ArrayList;
import java.util.List;

public class CreateListingDTO {
    //    @OneToOne
//    private Location location;
    @NotEmpty
    private String brand;
    @NotEmpty
    private Integer size;
    @NotEmpty
    private String style;
    @NotEmpty
    private String condition;

//    @OneToMany(mappedBy = "listing")
//    private List<Image> images = new ArrayList<>();

    public CreateListingDTO() {

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
