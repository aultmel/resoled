package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query("SELECT b.name, COUNT(b.name) AS frequency " +
            "FROM Brand b " +
            "GROUP BY b.name " +
            "ORDER BY COUNT(b.name) DESC LIMIT 5")
    List<Object[]> findPopularBrands();

    Boolean existsByName(String name);

    Brand findByName(String name);

    @Query("SELECT name FROM Brand")
    List<String> getNames();
}