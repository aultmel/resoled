package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {
    @Query("SELECT s.name, COUNT(s.name) AS frequency " +
            "FROM Style s " +
            "GROUP BY s.name " +
            "ORDER BY COUNT(s.name) DESC")
    List<Object[]> findPopularStyles();

    Boolean existsByName(String name);

    Style findByName(String name);

    @Query("SELECT name FROM Style")
    List<String> getNames();
}