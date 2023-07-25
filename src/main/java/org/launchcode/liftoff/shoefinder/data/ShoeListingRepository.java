package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeListingRepository extends JpaRepository<ShoeListing, Long> {
}