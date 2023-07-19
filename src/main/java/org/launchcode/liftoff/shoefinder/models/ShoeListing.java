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

    @ManyToOne
    private Location location;

    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Size size;
    @ManyToOne
    private Style style;
    @ManyToOne
    private Condition condition;
    @OneToMany(mappedBy = "listing")
    private List<Image> images = new ArrayList<>();

//    private Integer zipcode;

    //Empty Constructor
    public ShoeListing() {

    }

    // Constructor with parameters
    public ShoeListing(Long id, Brand brand, Size size, Style style, Condition condition, Location location, UserEntity userEntity) {
        this.id = id;
        this.brand = brand;
        this.size = size;
        this.style = style;
        this.condition = condition;
//        this.zipcode = zipcode;
        this.location = location;
        this.userEntity = userEntity;
    }

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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    //returns list of image byte[] data
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}