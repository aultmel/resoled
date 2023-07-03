package org.launchcode.liftoff.shoefinder;

import jakarta.persistence.*;

@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public String brand;
    public int size;
    public String style;
    public String addressInput;
    public int latitude;
    public int longitude;

    @ManyToOne
    public User user;

}
