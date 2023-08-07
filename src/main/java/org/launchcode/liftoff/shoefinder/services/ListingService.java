package org.launchcode.liftoff.shoefinder.services;


import org.launchcode.liftoff.shoefinder.data.*;
import org.launchcode.liftoff.shoefinder.models.*;
import org.launchcode.liftoff.shoefinder.models.dto.CreateListingDTO;
import org.launchcode.liftoff.shoefinder.models.dto.SearchListingsDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Service
public class ListingService {

    private final ShoeListingRepository shoeListingRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final StyleRepository styleRepository;
    private final ImageRepository imageRepository;

    // Constructor to inject the required repositories.

    public ListingService(ShoeListingRepository shoeListingRepository, UserRepository userRepository,
                          BrandRepository brandRepository, StyleRepository styleRepository, ImageRepository imageRepository) {
        this.shoeListingRepository = shoeListingRepository;
        this.userRepository = userRepository;
        this.brandRepository = brandRepository;
        this.styleRepository = styleRepository;
        this.imageRepository = imageRepository;
    }

    // Method to save a new shoe listing along with associated image files.
    public void saveListing(CreateListingDTO createListingDTO, MultipartFile[] files) {
        // Get the current user's entity from the UserRepository using the session user's username.

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
        // Save associated images to the specified directory and store their information in the ImageRepository.
            String directoryPath = "src\\main\\resources\\static\\images\\listing-images";


            for(MultipartFile imageFile: files) {
                ImageLocal imageLocal = new ImageLocal();
                imageLocal.setImageFile(imageFile);
                imageLocal.setListing(shoeListing);
                imageRepository.save(imageLocal);
                imageLocal.saveImageLocally(files);

            }

    }

    // Method to filter shoe listings based on search criteria.

    public List<ShoeListing> filterListings(SearchListingsDTO searchListingsDTO) {
        List<ShoeListing> filteredListings = new ArrayList<>();
        // Find shoe listings for the given gender and add them to the filtered list.

        for (String gender : searchListingsDTO.getGenders()) {
            List<ShoeListing> genderList  = shoeListingRepository.findByGender(gender);
            for (ShoeListing shoeListing : genderList) {
                filteredListings.add(shoeListing);
            }
        }

        return filteredListings;
    }





}