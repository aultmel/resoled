package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
//    @Query(nativeQuery = true, value = "SELECT name, COUNT(name) AS frequency\n" +
//            "FROM brand\n" +
//            "GROUP BY name\n" +
//            "ORDER BY frequency DESC\n" +
//            "LIMIT 5;")
@Query("SELECT b.name, COUNT(b.name) AS frequency " +
        "FROM Brand b " +
        "GROUP BY b.name " +
        "ORDER BY COUNT(b.name) DESC")
    List<Object[]> findPopularBrands();
}
