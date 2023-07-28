package org.launchcode.liftoff.shoefinder.services;

import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateListingDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.stereotype.Service;


@Service
public class ListingService {

    private final ShoeListingRepository shoeListingRepository;
    private final UserRepository userRepository;

    public ListingService(ShoeListingRepository shoeListingRepository, UserRepository userRepository) {
        this.shoeListingRepository = shoeListingRepository;
        this.userRepository = userRepository;
    }


    public void saveListing(CreateListingDTO createListingDTO) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);

        ShoeListing shoeListing = new ShoeListing();
        shoeListing.setUserEntity(userEntity);
//        shoeListing.setSize();
//        shoeListing.setStyle();
//        shoeListing.setBrand();
//        shoeListing.setCondition();


        shoeListingRepository.save(shoeListing);
    }




}
