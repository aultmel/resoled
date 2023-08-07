package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Location {
    @Id
    @GeneratedValue
    private long id;

    private String zipCode;

//    private double latitude;
//    private double longitude;
//    private int streetNumber;
//    private String streetName;
//    private String city;
//    private String state;

    public Location() {}


    public long getId() { return id; }

    public String getZipCode() { return zipCode; }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

}