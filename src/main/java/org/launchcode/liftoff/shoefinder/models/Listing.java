package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "List")
public class Listing {

private String brand;

private Integer size;

private Integer zipcode;

public Listing () {

}
public Listing(String brand, Integer size, Integer zipcode){
    this.brand = brand;
    this.size = size;
    this.zipcode = zipcode;
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
