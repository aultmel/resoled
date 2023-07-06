package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
