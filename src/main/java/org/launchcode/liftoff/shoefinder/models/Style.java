package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Style {

    @Id
    @GeneratedValue
    private Long id;
    private static final String KEY_ = "";
    private String name;
    private static final List<String> styleNames = new ArrayList<>();

    static {
        styleNames.add("Running");
        styleNames.add("Casual");
        styleNames.add("Athletic");
        styleNames.add("Sandals");
        styleNames.add("Formal");
    }
    public List<String> getStyleNames() {
        return styleNames;
    }
}