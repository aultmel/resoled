package org.launchcode.liftoff.shoefinder.data;


import org.launchcode.liftoff.shoefinder.models.Brand;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShoeListingRepository  extends JpaRepository<ShoeListing, Long> {

    List<ShoeListing> findByGender(String gender);
    List<ShoeListing> findByBrand(Brand brand);
    List<ShoeListing> findBySize(String size);
    List<ShoeListing> findByStyle(Style style);
    List<ShoeListing> findByCondition(String condition);


}
