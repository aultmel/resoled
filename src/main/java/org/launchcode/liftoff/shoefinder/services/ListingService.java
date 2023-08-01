package org.launchcode.liftoff.shoefinder.services;


import org.launchcode.liftoff.shoefinder.data.BrandRepository;
import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.data.StyleRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Brand;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.Style;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateListingDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Service
public class ListingService {

    private final ShoeListingRepository shoeListingRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final StyleRepository styleRepository;

    public ListingService(ShoeListingRepository shoeListingRepository, UserRepository userRepository, BrandRepository brandRepository, StyleRepository styleRepository) {
        this.userRepository = userRepository;
        this.shoeListingRepository = shoeListingRepository;
        this.brandRepository = brandRepository;
        this.styleRepository = styleRepository;
    }


    public void saveListing(CreateListingDTO createListingDTO) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);

        ShoeListing shoeListing = new ShoeListing();
        shoeListing.setUserEntity(userEntity);
//conditional for if the brand already exists if not adds it to the repository
        if (brandRepository.existsByName(createListingDTO.getBrand().toUpperCase())) {
            Brand brand = brandRepository.findByName(createListingDTO.getBrand().toUpperCase());
            shoeListing.setBrand(brand);
        } else {
            Brand brand = new Brand();
            brand.setName(createListingDTO.getBrand().toUpperCase());
            shoeListing.setBrand(brand);
            brandRepository.save(brand);
        }
//conditional for if the style already exists if not adds it to the repository
        if(styleRepository.existsByName(createListingDTO.getStyle().toUpperCase())){
            Style style = styleRepository.findByName(createListingDTO.getStyle().toUpperCase());
            shoeListing.setStyle(style);
        } else {
            Style style = new Style();
            style.setName(createListingDTO.getStyle().toUpperCase());
            shoeListing.setStyle(style);
            styleRepository.save(style);
        }
// Sets size gender and condition
        shoeListing.setSize(createListingDTO.getSize());
        shoeListing.setGender(createListingDTO.getGender());
        shoeListing.setCondition(createListingDTO.getCondition());
// creates a message saying the user has created a class


        shoeListingRepository.save(shoeListing);
    }

}