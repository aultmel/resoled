package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

@Entity
public class Brand {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    public enum BrandName {
        ADIDAS("Adidas"), NIKE("Nike"), GUCCI("Gucci"), JIMMYCHOO("Jimmy Choo"), CALVINKLIEN("Calvin Klien"), PUMA("Puma"), CROC("Croc"), ARIAT("Ariat"), REDWING("Redwing"), TIMBERLAND("Timberland"), VERSACE("Versace"), NEWBALANCE("New Balance"), CONVERSE("Converse"), CHINESELAUNDRY("Chinese Laundry"), HEELYS("Heelys"), JANSPORT("Jansport"), MADDENGIRL("Madden Girl"), REEBOK("Reebok"), VANS("Vans");

        private String value;

        BrandName(String value) {
            this.value = value;
        }

        //may switch to arrayList, double check
    }


    private BrandName brandName;

    public Brand(BrandName brandName) {this.brandName = brandName;}

    public Long getId() {
        return id;
    }

    public BrandName getBrandName() {
        return brandName;
    }

    public void setBrandName(BrandName brandName) {
        this.brandName = brandName;
    }
}

