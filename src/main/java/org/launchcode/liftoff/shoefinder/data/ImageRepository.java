package org.launchcode.liftoff.shoefinder.data;

//import org.launchcode.liftoff.shoefinder.models.Image;
import org.launchcode.liftoff.shoefinder.models.ImageLocal;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageLocal, Long> {



    //Optional<ImageLocal> findByFileName(String fileName);


}
