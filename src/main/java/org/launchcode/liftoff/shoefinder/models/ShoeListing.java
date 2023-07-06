package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;

@Entity
@Table(name = "List")
public class ShoeListing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String brand;
    private Integer size;
    private String style;
//    private String condition;
    private Integer zipcode;

    public ShoeListing () {

    }
    public ShoeListing(Integer id, String brand, Integer size, Integer zipcode, String style
//            , String condition
    ){
        this.id = id;
        this.brand = brand;
        this.size = size;
        this.zipcode = zipcode;
        this.style = style;
//        this.condition = condition;
    }

    public Integer getId() {
        return id;
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

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }
}