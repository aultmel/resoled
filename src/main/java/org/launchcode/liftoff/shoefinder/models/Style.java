package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

@Entity
public class Style {

    @Id
    @GeneratedValue
    private int Id;
    private static final String KEY_ = "";
    public enum styleType {
        RUNNING("Running"), HEELS("Heels"), WINGTIP("Wingtip"), OXFORD("Oxford"), BOOTS("Boots"), FLATS("Flats"), SANDALS("Sandals"), CLOGS("Clogs"), WATERSHOES("Water Shoes"), PLATFORMS("Platforms"), HIGHTOPS("Hightops"), TOESHOES("Toe Shoes"), MOONSHOES("Moonshoes"), LOAFERS("Loafers"), BALLET("Ballet"), TAP("Tap");


        private String value;
        private styleType(String value) {
            this.value = value;
        }
    };
}