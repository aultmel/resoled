package org.launchcode.liftoff.shoefinder.models;
//comments added
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "List")
public class ShoeListing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private UserEntity userEntity;

    @OneToOne
    private Location location;

    private String brand;

    private Integer size;

    private String style;

    private String conditionDTO;

//    @OneToMany(mappedBy = "listing")
//    private List<Image> images = new ArrayList<>();


    //Empty Constructor
    public ShoeListing() {

    }

    // Constructor with parameters
//    public ShoeListing(Long id, Brand brand, Size size, Style style, Condition condition, Location location, UserEntity userEntity) {
//        this.id = id;
//        this.brand = brand;
//        this.size = size;
//        this.style = style;
//        this.condition = condition;
//        this.location = location;
//        this.userEntity = userEntity;
//    }
    //
    //Getters and Setters Methods
    public Long getId() {
        return id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
        return conditionDTO;
    }

    public void setCondition(String condition) {
        this.conditionDTO = condition;
    }
//    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }
}