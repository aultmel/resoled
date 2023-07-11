package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


import java.util.ArrayList;

@Entity
public class Size {

    @Id
    @GeneratedValue
    private Long id;

    public enum mSizes {
        SEVEN(7.0), SEVENHALF(7.5), EIGHT(8.0), EIGHTHALF(8.5), NINE(9.0), NINEHALF(9.5), TEN(10.0), TENHALF(10.5), ELEVEN(11.0), ELEVENHALF(11.5), TWELVE(12.0),TWELVEHALF(12.5), THIRTEEN(13.0), THIRTEENHALF(13.5);
        private Double value;

        private mSizes(Double value) {
            this.value = value;
        }
    };

    public enum wSizes {
        FIVE(5.0), FIVEHALF(5.5), SIX(6.0), SIXHALF(6.5), SEVEN(7.0), SEVENHALF(7.5), EIGHT(8.0), EIGHTHALF(8.5), NINE(9.0), NINEHALF(9.5), TEN(10.0), TENHALF(10.5), ELEVEN(11.0);
        private Double value;

        private wSizes(Double value) {
            this.value = value;
        }
    };


}