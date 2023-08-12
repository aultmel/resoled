package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {
}
