package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Style {

    @Id
    @GeneratedValue
    private Long id;
    private static final String KEY_ = "";
    private String name;
    public enum styleType {
        RUNNING("Running"), HEELS("Heels"), WINGTIP("Wingtip"), OXFORD("Oxford"), BOOTS("Boots"), FLATS("Flats"), SANDALS("Sandals"), CLOGS("Clogs"), WATERSHOES("Water Shoes"), PLATFORMS("Platforms"), HIGHTOPS("Hightops"), TOESHOES("Toe Shoes"), MOONSHOES("Moonshoes"), LOAFERS("Loafers"), BALLET("Ballet"), TAP("Tap");


        private String value;
        private styleType(String value) {
            this.value = value;
        }
    };
}