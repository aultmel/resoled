package org.launchcode.liftoff.shoefinder.models;
//comments added
import jakarta.persistence.*;

@Entity
@Table(name = "List")
public class ShoeListing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private UserEntity userEntity;
    @OneToOne
    private Location location;

    private Brand brand;
    private Size size;
    private Style style;
    private Condition condition;
//    private Integer zipcode;

    //Empty Constructor
    public ShoeListing() {

    }

    // Constructor with parameters
    public ShoeListing(Integer id, Brand brand, Size size, Style style, Condition condition, Location location, UserEntity userEntity) {
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
    public Integer getId() {
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
}


