package org.launchcode.liftoff.shoefinder.models;
//comments added

import jakarta.persistence.*;


@Entity
@Table(name = "listing")
public class ShoeListing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Style style;

    @Column(name= "`condition`")
    private String condition;
    private String size;
    private String gender;
    private String title;

    @OneToOne( cascade = CascadeType.ALL)
    private ImageLocal imageLocal;

    //Empty Constructor
    public ShoeListing() {}

    //Getters and Setters Methods
    public Long getId() {
        return id;
    }

    public UserEntity getUserEntity() {return userEntity;}
    public void setUserEntity(UserEntity userEntity) {this.userEntity = userEntity;}

    public Brand getBrand() {return brand;}
    public void setBrand(Brand brand) {this.brand = brand;}

    public String getSize() {return size;}
    public void setSize(String size) {this.size = size;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}

    public Style getStyle() {return style;}
    public void setStyle(Style style) {this.style = style;}

    public String getCondition() {return condition;}
    public void setCondition(String condition) {this.condition = condition;}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public ImageLocal getImageLocal() { return imageLocal;  }
    public void setImageLocal(ImageLocal imageLocal) { this.imageLocal = imageLocal; }

}