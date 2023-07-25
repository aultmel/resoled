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
////        this.zipcode = zipcode;
//        this.location = location;
//        this.userEntity = userEntity;
//    }

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


//    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }
}

