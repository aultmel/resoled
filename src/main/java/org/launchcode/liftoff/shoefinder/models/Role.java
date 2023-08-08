package org.launchcode.liftoff.shoefinder.models;


import jakarta.persistence.*;


@Entity
@Table(name = "role")
public class Role {

    @Id
    private int id;

    private String name;


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
