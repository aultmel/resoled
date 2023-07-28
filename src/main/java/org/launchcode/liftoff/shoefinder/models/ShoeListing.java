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
     @ManyToOne
     private Brand brand;
     @ManyToOne
     private Size size;
     @ManyToOne
     private Style style;
     @ManyToOne
     private Condition condition;

    private Integer zipcode;


    @OneToMany(mappedBy = "listing")
    private List<ImageLocal> images = new ArrayList<>();

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

////        this.zipcode = zipcode;
//        this.location = location;
//        this.userEntity = userEntity;
//    }


    //Getters and Setters Methods


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    //returns list of image byte[] data
    public List<ImageLocal> getImages() {
        return images;
    }

    public void setImages(List<ImageLocal> images) {
        this.images = images;
    }

}


