package org.launchcode.liftoff.shoefinder.models.dto;

import com.google.maps.model.Distance;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;

public class ShoeListingDistanceDTO {
    private Distance distance;
    private ShoeListing shoeListing;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public ShoeListing getShoeListing() {
        return shoeListing;
    }

    public void setShoeListing(ShoeListing shoeListing) {
        this.shoeListing = shoeListing;
    }
}
