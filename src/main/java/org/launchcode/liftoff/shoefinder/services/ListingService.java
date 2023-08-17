package org.launchcode.liftoff.shoefinder.services;


import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.mysql.cj.util.StringUtils;
import org.launchcode.liftoff.shoefinder.data.*;
import org.launchcode.liftoff.shoefinder.models.*;
import org.launchcode.liftoff.shoefinder.models.dto.CreateListingDTO;
import org.launchcode.liftoff.shoefinder.models.dto.ShoeListingDistanceDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class ListingService {

    private final ShoeListingRepository shoeListingRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final StyleRepository styleRepository;
    private final StorageService storageService;

    // Constructor to inject the required repositories.


    public ListingService(ShoeListingRepository shoeListingRepository, UserRepository userRepository, BrandRepository brandRepository, StyleRepository styleRepository, StorageService storageService) {
        this.shoeListingRepository = shoeListingRepository;
        this.userRepository = userRepository;
        this.brandRepository = brandRepository;
        this.styleRepository = styleRepository;
        this.storageService = storageService;
    }

    // Method to save a new shoe listing along with associated image files.
    public void saveListing(CreateListingDTO createListingDTO, MultipartFile file) {
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
        shoeListing.setTitle(createListingDTO.getTitle());

        shoeListingRepository.save(shoeListing);
        // Save associated images to the specified directory and store their information in the ImageRepository.
            String directoryPath = "src\\main\\resources\\static\\images\\listing-images";

        try {
            storageService.saveListingImage(file , shoeListing);

        } catch (Exception e) {
//            message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
//            model.addAttribute("message", message);
        }

    }

    // Method to filter shoe listings based on search criteria.

    public List<ShoeListing> filterListings() throws IOException, InterruptedException, ApiException {
        List<ShoeListing> filteredListings = shoeListingRepository.findAll();
        List<List<ShoeListing>> activeFilters = new ArrayList<>();
        List<ShoeListing> itemsToRemove = new ArrayList<>();

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);


        CurrentSearch currentSearch = userEntity.getCurrentSearch();

        // if user included gender filters
        ArrayList<String> genderArrayList = new ArrayList<>();
        if(currentSearch.getSearchGenders() != null) {
            String gendersString = currentSearch.getSearchGenders();
            String[] gendersArray = gendersString.split(",");
            for (String gender : gendersArray) {
                genderArrayList.add(gender);
            }
        }
        if (!genderArrayList.isEmpty()) {
            List<ShoeListing> genderList = new ArrayList<>();
            //for each gender user selected
            for (String gender : genderArrayList) {
                //find all listings with that gender and add to genderList
                genderList.addAll(shoeListingRepository.findByGender(gender));
            }
            // if no matching listings
            if (genderList.isEmpty()) {
                return Collections.emptyList();
            }
            activeFilters.add(genderList);
        }

        String brand = currentSearch.getSearchBrand();
        if (!StringUtils.isNullOrEmpty(brand)) {
            List<ShoeListing> brandList = new ArrayList<>();

            brandList.addAll(shoeListingRepository.findByBrand(brandRepository.findByName(brand)));

            if (brandList.isEmpty()) {
                return Collections.emptyList();
            }
            activeFilters.add(brandList);
        }


        ArrayList<String> sizeArrayList = new ArrayList<>();
        if(currentSearch.getSearchSizes() != null) {
            String sizesString = currentSearch.getSearchSizes();
            String[] sizesArray = sizesString.split(",");
            for (String size : sizesArray) {
                sizeArrayList.add(size);
            }
        }
        if (!sizeArrayList.isEmpty()) {
            List<ShoeListing> sizeList = new ArrayList<>();
            for (String size : sizeArrayList) {
                sizeList.addAll(shoeListingRepository.findBySize(size));
            }
            if (sizeList.isEmpty()) {
                return Collections.emptyList();
            }
            activeFilters.add(sizeList);
        }



        if (!StringUtils.isNullOrEmpty(currentSearch.getSearchStyle())) {
            List<ShoeListing> styleList = new ArrayList<>();

            styleList.addAll(shoeListingRepository.findByStyle(styleRepository.findByName(currentSearch.getSearchStyle())));

            if (styleList.isEmpty()) {
                return Collections.emptyList();
            }
            activeFilters.add(styleList);
        }

        if (!StringUtils.isNullOrEmpty(currentSearch.getSearchCondition())) {
            List<ShoeListing> conditionList = new ArrayList<>();

            conditionList.addAll(shoeListingRepository.findByCondition(currentSearch.getSearchCondition()));

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

        //remove all non-matching listings
        filteredListings.removeAll(itemsToRemove);
        List<ShoeListing> sortedListings = new ArrayList<>();


        if (currentSearch != null && currentSearch.getSearchZipCode() != null && !currentSearch.getSearchZipCode().isEmpty()) {
            LatLng searchLatLng = new LatLng();
            LatLng listingLatLng = new LatLng();
            try {
                GeoApiContext ctx = new GeoApiContext.Builder()
                        .apiKey(System.getenv("GOOGLE_API_KEY"))
                        .build();

                GeocodingResult[] results = GeocodingApi.geocode(ctx, currentSearch.getSearchZipCode()).await();
                if (results.length > 0) {
                    searchLatLng = results[0].geometry.location;
                }

                List<ShoeListingDistanceDTO> locationFiltered = new ArrayList<>();
                for (ShoeListing listing : filteredListings) {
                    ShoeListingDistanceDTO tempListing = new ShoeListingDistanceDTO();
                    tempListing.setShoeListing(listing);
                    String listingZip = listing.getUserEntity().getLocation().getZipCode();
                    GeocodingResult[] listingResults = GeocodingApi.geocode(ctx, listingZip).await();
                    if (listingResults.length > 0) {
                        listingLatLng = listingResults[0].geometry.location;
                    }

                    DistanceMatrix distanceResults = new DistanceMatrixApiRequest(ctx)
                            .origins(searchLatLng)
                            .destinations(listingLatLng)
                            .mode(TravelMode.DRIVING)
                            .await();


                    DistanceMatrixElement element = distanceResults.rows[0].elements[0];
                    System.out.println(element);
                    if (element.status == DistanceMatrixElementStatus.OK) {
                        Distance distance = element.distance;
                        tempListing.setDistance(distance);
                        locationFiltered.add(tempListing);

                    }

                }
                Collections.sort(locationFiltered, Comparator.comparingDouble(dto -> dto.getDistance().inMeters));
                for (ShoeListingDistanceDTO dto : locationFiltered) {
                    if (!currentSearch.getSearchDistance().isEmpty() && !currentSearch.getSearchDistance().equals("0")) {
                        Integer distance = Integer.parseInt(currentSearch.getSearchDistance());
                        if (dto.getDistance().inMeters * 0.00062137 < distance) {
                            sortedListings.add(dto.getShoeListing());
                        }
                    } else {
                        sortedListings.add(dto.getShoeListing());
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
                }
        } else {
            for (ShoeListing listing : filteredListings) {
                sortedListings.add(listing);
            }
        }
        return sortedListings;
    }

}