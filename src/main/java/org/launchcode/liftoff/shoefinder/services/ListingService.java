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
import java.util.Collections;
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
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);

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
        List<ShoeListing> filteredListings = shoeListingRepository.findAll();
        List<List<ShoeListing>> activeFilters = new ArrayList<>();
        List<ShoeListing> itemsToRemove = new ArrayList<>();

        // if user included gender filters
        if (!searchListingsDTO.getGenders().isEmpty()) {
            List<ShoeListing> genderList = new ArrayList<>();
            //for each gender user selected
            for (String gender : searchListingsDTO.getGenders()) {
                //find all listings with that gender and add to genderList
                genderList.addAll(shoeListingRepository.findByGender(gender));
            }
            // if no matching listings
            if (genderList.isEmpty()) {
                return Collections.emptyList();
            }
            activeFilters.add(genderList);
        }

        if (!searchListingsDTO.getBrands().isEmpty()) {
            List<ShoeListing> brandList = new ArrayList<>();
            for (String brand : searchListingsDTO.getBrands()) {
                brandList.addAll(shoeListingRepository.findByBrand(brandRepository.findByName(brand)));
            }
            if (brandList.isEmpty()) {
                return Collections.emptyList();
            }
            activeFilters.add(brandList);
        }

        if (!searchListingsDTO.getSizes().isEmpty()) {
            List<ShoeListing> sizeList = new ArrayList<>();
            for (String size : searchListingsDTO.getSizes()) {
                sizeList.addAll(shoeListingRepository.findBySize(size));
            }
            if (sizeList.isEmpty()) {
                return Collections.emptyList();
            }
            activeFilters.add(sizeList);
        }

        if (!searchListingsDTO.getStyles().isEmpty()) {
            List<ShoeListing> styleList = new ArrayList<>();
            for (String style : searchListingsDTO.getStyles()) {
                styleList.addAll(shoeListingRepository.findByStyle(styleRepository.findByName(style)));
            }
            if (styleList.isEmpty()) {
                return Collections.emptyList();
            }
            activeFilters.add(styleList);
        }

        if (searchListingsDTO.getCondition() != null) {
            List<ShoeListing> conditionList = new ArrayList<>();

            conditionList.addAll(shoeListingRepository.findByCondition(searchListingsDTO.getCondition()));

            if (conditionList.isEmpty()) {
                return Collections.emptyList();
            }
            activeFilters.add(conditionList);
        }
        //for every listing in the database
        for (ShoeListing listing : filteredListings) {
            boolean shouldRemove = false;
            //for every filterList (brandList, genderList, etc)
            for (List<ShoeListing> filterList : activeFilters) {
                //if the doesn't have that listing
                if (!filterList.contains(listing)) {
                    // set removal boolean to true
                    shouldRemove = true;
                    break;
                }
            }
            //if the listing should be removed
            if (shouldRemove) {
                //add it to remove list
                itemsToRemove.add(listing);
            }
        }

//        if (!searchListingsDTO.getZipCode().isEmpty()) {
//
//        }

        //remove all non-matching listings
        filteredListings.removeAll(itemsToRemove);
        return filteredListings;
    }
}