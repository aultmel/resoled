package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.ProfileImage;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

}
