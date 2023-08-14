package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.CurrentSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentSearchRepository extends JpaRepository<CurrentSearch, Long> {
}
