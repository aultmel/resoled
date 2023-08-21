package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class CurrentSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String searchTerm;

    private String searchBrand;

    private String searchSizes;

    private String searchStyle;

    private String searchCondition;

    private String searchGenders;

    private String searchZipCode;

    private String searchDistance;

    public CurrentSearch() {  }

    public Long getId() {
        return id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchBrand() {
        return searchBrand;
    }

    public void setSearchBrand(String searchBrand) {
        this.searchBrand = searchBrand;
    }

    public String getSearchSizes() {
        return searchSizes;
    }

    public void setSearchSizes(String searchSizes) {
        this.searchSizes = searchSizes;
    }

    public String getSearchStyle() {
        return searchStyle;
    }

    public void setSearchStyle(String searchStyle) {
        this.searchStyle = searchStyle;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getSearchGenders() {
        return searchGenders;
    }

    public void setSearchGenders(String searchGenders) {
        this.searchGenders = searchGenders;
    }

    public String getSearchZipCode() {
        return searchZipCode;
    }

    public void setSearchZipCode(String searchZipCode) {
        this.searchZipCode = searchZipCode;
    }

    public String getSearchDistance() {
        return searchDistance;
    }

    public void setSearchDistance(String searchDistance) {
        this.searchDistance = searchDistance;
    }
}
