package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.Formula;

@Entity
public class Brand {

    @Id
    @GeneratedValue
    private Long id;
    @Formula("(SELECT COUNT(*) FROM Shoe s WHERE s.brand_id = id)")
    private int popularity;
    private String name;
    public enum brandName {
        ADIDAS("Adidas"), NIKE("Nike"), GUCCI("Gucci"), JIMMYCHOO("Jimmy Choo"), CALVINKLIEN("Calvin Klien"), PUMA("Puma"), CROC("Croc"), ARIAT("Ariat"), REDWING("Redwing"), TIMBERLAND("Timberland"), VERSACE("Versace"), NEWBALANCE("New Balance"), CONVERSE("Converse"), CHINESELAUNDRY("Chinese Laundry"), HEELYS("Heelys"), JANSPORT("Jansport"), MADDENGIRL("Madden Girl"), REEBOK("Reebok"), VANS("Vans");

        private String value;

        private brandName(String value) {
            this.value = value;
        }

        //may switch to arrayList, double check
    }


}

