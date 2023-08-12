package org.launchcode.liftoff.shoefinder.data;


//import org.launchcode.liftoff.shoefinder.models.Image;

//import org.launchcode.liftoff.shoefinder.models.Image;
import org.launchcode.liftoff.shoefinder.models.ImageLocal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageLocal, Long> {

    //Optional<ImageLocal> findByFileName(String fileName);


//    Optional<Image> findByFileName(String fileName);

}

