package org.launchcode.liftoff.shoefinder.models;

import jakarta.annotation.Generated;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

public class Brand {
    @Id
    @GeneratedValue
    private int Id;


    public enum brandName {
        ADIDAS("Adidas"), NIKE("Nike"), GUCCI("Gucci"), JIMMYCHOO("Jimmy Choo"), CALVINKLIEN("Calvin Klien"), PUMA("Puma"), CROC("Croc"), ARIAT("Ariat"), REDWING("Redwing"), TIMBERLAND("Timberland"), VERSACE("Versace"), NEWBALANCE("New Balance"), CONVERSE("Converse"), CHINESELAUNDRY("Chinese Laundry"), HEELYS("Heelys"), JANSPORT("Jansport"), MADDENGIRL("Madden Girl"), REEBOK("Reebok"), VANS("Vans");

        private String value;

        private brandName(String value) {
            this.value = value;
        }

        //may switch to arrayList, double check
    }


}


