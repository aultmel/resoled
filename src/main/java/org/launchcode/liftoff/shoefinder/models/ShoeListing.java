package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;

@Entity
@Table(name = "List")
public class ShoeListing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String brand;
    private Integer size;
    private Integer zipcode;

public ShoeListing() {

}
public ShoeListing(String brand, Integer size, Integer zipcode){
    this.brand = brand;
    this.size = size;
    this.zipcode = zipcode;
}

    public int getId() {
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
