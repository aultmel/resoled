package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.Brand;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository {
    @Query(nativeQuery = true, value = "SELECT * FROM brand ORDER BY popularity DESC")
    List<Brand> findPopularBrands();
}
