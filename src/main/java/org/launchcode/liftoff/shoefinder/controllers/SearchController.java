package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.BrandRepository;
import org.launchcode.liftoff.shoefinder.data.StyleRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Brand;
import org.launchcode.liftoff.shoefinder.models.Style;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.SearchListingsDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.launchcode.liftoff.shoefinder.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

import static org.launchcode.liftoff.shoefinder.constants.ListingConstants.*;

@Controller
public class SearchController {

    private final BrandRepository brandRepository;
    private final StyleRepository styleRepository;
    private final ListingService listingService;
    private final UserRepository userRepository;

    public SearchController(BrandRepository brandRepository, StyleRepository styleRepository, ListingService listingService, UserRepository userRepository) {
        this.brandRepository = brandRepository;
        this.styleRepository = styleRepository;
        this.listingService = listingService;
        this.userRepository = userRepository;
    }

    @GetMapping("/search")
    public String search(Model model){
        SearchListingsDTO searchListingsDTO = new SearchListingsDTO();
        model.addAttribute("searchListingsDTO", searchListingsDTO);

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        //Api urls for suggestions
        model.addAttribute("brandSuggestionsUrl", "http://localhost:8080/api/brandSuggestion");
        model.addAttribute("styleSuggestionsUrl", "http://localhost:8080/api/styleSuggestion");

        //Grab 5 most recurring brands from database
        List<Object[]> topBrandsData = brandRepository.findPopularBrands();
        List<String> topBrands = new ArrayList<>();

        //For each brand object, grab the name and store in topBrands
        for (Object[] brand : topBrandsData) {
            String brandName = (String) brand[0];
            topBrands.add(brandName);
        }
        //Add topBrands to the model for search.html
        if (topBrands.size() < 5) {
            Brand brand = new Brand();
            model.addAttribute("topBrands", brand.getBrandNames());
        } else {
            model.addAttribute("topBrands", topBrands);
        }


        //Grab 5 most recurring styles from database
        List<Object[]> topStylesData = styleRepository.findPopularStyles();
        List<String> topStyles = new ArrayList<>();

        //For each style object, grab the name and store in topStyles
        for (Object[] style : topStylesData) {
            String styleName = (String) style[0];
            topStyles.add(styleName);
        }
        if (topStyles.size() < 5) {
            Style style = new Style();
            model.addAttribute("topStyles", style.getStyleNames());
        } else {
            model.addAttribute("topStyles", topStyles);
        }

        //Create list of shoe sizes for the model
        List<String> shoeSizeList = SIZE_LIST;
        model.addAttribute("allSizes", shoeSizeList);

        model.addAttribute("normalSizes", NORMAL_SIZE_LIST);

        //Create list of conditions for the model
        List<String> conditionList = CONDITION_LIST;
        model.addAttribute("conditions", conditionList);

        return "search";
    }

//    @GetMapping("/listingsearch")
//    public String searchPostMap(@ModelAttribute("searchListingsDTO") SearchListingsDTO searchListingsDTO, Model model) {
//        List<ShoeListing> filteredListings = listingService.filterListings(searchListingsDTO);
//        model.addAttribute("allListings", filteredListings);
//
//        String username = SecurityUtility.getSessionUser();
//        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
//        model.addAttribute("userEntity", userEntity);
//
//        return "listings/listings";
//    }



}