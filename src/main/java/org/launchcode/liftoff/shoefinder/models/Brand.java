package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Brand {

    @Id
    @GeneratedValue
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

