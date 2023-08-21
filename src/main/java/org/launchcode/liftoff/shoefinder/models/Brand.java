package org.launchcode.liftoff.shoefinder.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private static final List<String> brandNames = new ArrayList<>();

    static {
        brandNames.add("Adidas");
        brandNames.add("Nike");
        brandNames.add("Reebok");
        brandNames.add("Puma");
        brandNames.add("Under Armour");
    }

    public Brand() {}

    public List<String> getBrandNames() {
        return brandNames;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
