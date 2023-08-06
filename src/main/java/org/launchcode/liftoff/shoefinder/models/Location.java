package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Location {
    @Id
    @GeneratedValue
    private long id;

    private double latitude;
    private double longitude;
    private int streetNumber;
    private String streetName;
    private String city;
    private String zipCode;
    private String state;

    public Location() {
    }

    public Location(double latitude, double longitude, int streetNumber, String streetName, String city, String zipCode, String state) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}