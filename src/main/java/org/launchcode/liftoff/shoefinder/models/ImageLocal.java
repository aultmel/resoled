package org.launchcode.liftoff.shoefinder.models;


import jakarta.persistence.*;


@Entity
public class ImageLocal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String url;


    public ImageLocal() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}

