package org.launchcode.liftoff.shoefinder.services;

import org.apache.catalina.User;
import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Role;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateListingDTO;
import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
@Service
public class ListingService {

    private final ShoeListingRepository shoeListingRepository;
    private final UserRepository userRepository;

    public ListingService(ShoeListingRepository shoeListingRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.shoeListingRepository = shoeListingRepository;
    }

    public void saveListing(CreateListingDTO createListingDTO) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);

        ShoeListing shoeListing = new ShoeListing();

        shoeListing.setUserEntity(userEntity);
        shoeListing.setBrand(createListingDTO.getBrand());
        shoeListing.setSize(createListingDTO.getSize());
        shoeListing.setCondition(createListingDTO.getCondition());
        shoeListing.setStyle(createListingDTO.getStyle());



        shoeListingRepository.save(shoeListing);
    }
}
