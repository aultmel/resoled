package org.launchcode.liftoff.shoefinder.controllers;


import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import jakarta.validation.Valid;
import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.CurrentSearch;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateListingDTO;
import org.launchcode.liftoff.shoefinder.models.dto.SearchListingsDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.launchcode.liftoff.shoefinder.services.ListingService;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.launchcode.liftoff.shoefinder.constants.ListingConstants.GENDER_LIST;
import static org.launchcode.liftoff.shoefinder.constants.ListingConstants.SIZE_LIST;


@Controller
@RequestMapping("/listings")
public class ListingController {

    String uploadDir = "files";
    private final ListingService listingService;
    private final ShoeListingRepository shoeListingRepository;
    private final UserRepository userRepository;

    // Constructor to inject ListingService and ShoeListingRepository dependencies.

    public ListingController(ListingService listingService, ShoeListingRepository shoeListingRepository, UserRepository userRepository) {
        this.listingService = listingService;
        this.shoeListingRepository = shoeListingRepository;
        this.userRepository = userRepository;
    }


    // Handler method to display all shoe listings.

    @GetMapping("/")
    public String displayAllListings(Model model) {
        return getOneListingsPage(model, 1);
    }

    @GetMapping("/listings")
    public String messagesGetMapping(Model model) {
        return getOneListingsPage(model, 1);
    }

    @GetMapping("/listingSearch")
    public String listingSearchMapping(Model model) throws IOException, InterruptedException, ApiException {
        return getOneListingSearchPage(model, 1);
    }

    // Handler method to display details of a specific shoe listing.
    @GetMapping("/details")
    public String displayListingDetails(@RequestParam Long listingId, Model model) {
        Optional<ShoeListing> result = shoeListingRepository.findById(listingId);
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);
        LatLng latLng = new LatLng();



        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid ShoeListing ID: " + listingId);
        } else {
            ShoeListing shoeListing = result.get();
            String zipCode = shoeListing.getUserEntity().getLocation().getZipCode();

            try {
                GeoApiContext ctx = new GeoApiContext.Builder()
                        .apiKey(System.getenv("GOOGLE_API_KEY"))
                        .build();

                GeocodingResult[] results = GeocodingApi.geocode(ctx, zipCode).await();
                if (results.length > 0) {
                    latLng = results[0].geometry.location;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
            model.addAttribute("latitude", latLng.lat);
            model.addAttribute("longitude", latLng.lng);
            model.addAttribute("title", shoeListing.getTitle());
            model.addAttribute("listing", shoeListing);
            model.addAttribute("apiKey", System.getenv("GOOGLE_API_KEY"));
        }
            return "listings/listing";


    }
    // Handler method to display the shoe listing creation form.

    @GetMapping("/create")
    public String showListingForm(Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        model.addAttribute("genderList", GENDER_LIST);
        model.addAttribute("sizeList", SIZE_LIST);
        model.addAttribute("createListingDTO", new CreateListingDTO());

        //Api urls for suggestions
        model.addAttribute("brandSuggestionsUrl", "http://localhost:8080/api/brandSuggestion");
        model.addAttribute("styleSuggestionsUrl", "http://localhost:8080/api/styleSuggestion");

        return "listings/create";
    }

    // Handler method to create a new shoe listing.

    @PostMapping("/create")
    public String createListing(@Valid @ModelAttribute("createListingDTO") CreateListingDTO createListingDTO, BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes,
                                @RequestParam("file") MultipartFile file) {

        model.addAttribute("genderList", GENDER_LIST);
        model.addAttribute("sizeList", SIZE_LIST);
        model.addAttribute("createListingDTO", createListingDTO);
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        //Api urls for suggestions
        model.addAttribute("brandSuggestionsUrl", "http://localhost:8080/api/brandSuggestion");
        model.addAttribute("styleSuggestionsUrl", "http://localhost:8080/api/styleSuggestion");

        if (bindingResult.hasErrors()) {
            return "listings/create";
        }

        if (createListingDTO.getSize().equals("")) {
            bindingResult.rejectValue("size", "size.invalid", "Size is required");
            ;
            return "listings/create";
        }
        if (createListingDTO.getGender().equals("")) {
            bindingResult.rejectValue("gender", "gender.invalid", "Size is required");
            ;
            return "listings/create";
        }


        redirectAttributes.addFlashAttribute("message", "Shoe Listing Created");
        listingService.saveListing(createListingDTO, file);


        // Redirect to a success page
        return "redirect:../home";
    }


    // pagenation for all listings
    @GetMapping("/listings/page/{pageNumber}")
    public String getOneListingsPage(Model model, @PathVariable("pageNumber") int currentPage) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        List<ShoeListing> allShoeListings = shoeListingRepository.findAll();

        PagedListHolder<ShoeListing> pagedListHolder = new PagedListHolder<>(allShoeListings);
        pagedListHolder.setPage(currentPage - 1);
        pagedListHolder.setPageSize(6);

        List<ShoeListing> pageSlice = pagedListHolder.getPageList();
        Pageable pageable = PageRequest.of(currentPage - 1, 6);

        Page<ShoeListing> pageShoeListings = new PageImpl<>(pageSlice, pageable, allShoeListings.size());

        // Creating a pageable framework from a list of Listings
        // number of items on page is set by the size parameter of the PageRequest.of()

        int totalPages = pageShoeListings.getTotalPages();
        long totalItems = pageShoeListings.getTotalElements();

        //Get content of the list it will be the size set by the Pageable
        List<ShoeListing> shoeListingList = pageShoeListings.getContent();

        model.addAttribute("pageShoeListings", pageShoeListings);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("shoeListingList", shoeListingList);

        //Number of pages total that will be listed at once in the pagination menu.  Keep an even number for current code configuration.
        int paginationMenuTotalVisible = 4;
        model.addAttribute("paginationMenuTotalVisible", paginationMenuTotalVisible);
        model.addAttribute("paginationMenuSplitSidesVisible", paginationMenuTotalVisible / 2);

        return "listings/listings";
    }


    @PostMapping("/listingSearch")
    public String getOneListingSearchPage(@ModelAttribute("searchListingsDTO") SearchListingsDTO searchListingsDTO, Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);
        model.addAttribute("searchListingsDTO", searchListingsDTO);

        // setting search to user to keep search terms

        CurrentSearch currentSearch = new CurrentSearch();
        currentSearch.setSearchTerm(searchListingsDTO.getSearchTerm());
        currentSearch.setSearchCondition(searchListingsDTO.getCondition());
        currentSearch.setSearchBrand(searchListingsDTO.getBrand());
        currentSearch.setSearchStyle(searchListingsDTO.getStyle());
        currentSearch.setSearchZipCode(searchListingsDTO.getZipCode());
        currentSearch.setSearchDistance(searchListingsDTO.getDistance());

        // saving gender list as a comma separated string
        if (!searchListingsDTO.getGenders().isEmpty()) {
            List<String> gendersList = searchListingsDTO.getGenders();
            StringBuilder genderBuilder = new StringBuilder();
            for (String size : gendersList) {
                genderBuilder.append(size).append(",");
            }
            genderBuilder.deleteCharAt(genderBuilder.length() - 1);
            String gendersString = genderBuilder.toString();
            currentSearch.setSearchGenders(gendersString);
        }

        // saving size list as a comma separated string
        if (!searchListingsDTO.getSizes().isEmpty()) {

            List<String> sizesList = searchListingsDTO.getSizes();
            StringBuilder sizeBuilder = new StringBuilder();
            for (String size : sizesList) {
                sizeBuilder.append(size).append(",");
            }
            sizeBuilder.deleteCharAt(sizeBuilder.length() - 1);
            String sizesString = sizeBuilder.toString();
            currentSearch.setSearchSizes(sizesString);

        }

        userEntity.setCurrentSearch(currentSearch);
        userRepository.save(userEntity);

        return "redirect:../listings/listingSearch/page/1";
    }


    @GetMapping("/listingSearch/page/{pageNumber}")
    public String getOneListingSearchPage(Model model, @PathVariable("pageNumber") int currentPage) throws IOException, InterruptedException, ApiException {


        List<ShoeListing> allShoeListings = listingService.filterListings();

        PagedListHolder<ShoeListing> pagedListHolder = new PagedListHolder<>(allShoeListings);
        pagedListHolder.setPage(currentPage - 1);
        pagedListHolder.setPageSize(6);

        List<ShoeListing> pageSlice = pagedListHolder.getPageList();
        Pageable pageable = PageRequest.of(currentPage - 1, 6);

        Page<ShoeListing> pageShoeListings = new PageImpl<>(pageSlice, pageable, allShoeListings.size());

        // Creating a pageable framework from a list of Listings
        // Sorting so that list of MessageChains userEntityMessageChains is in order of the MessageChain with the
        // newest message is first on the list and the MessageChain with the latest message is at the end of the list.
        // number of items on page is set by the size parameter of the PageRequest.of()

        int totalPages = pageShoeListings.getTotalPages();
        long totalItems = pageShoeListings.getTotalElements();

        //Get content of the list it will be the size set by the Pageable
        List<ShoeListing> shoeListingList = pageShoeListings.getContent();

        model.addAttribute("pageShoeListings", pageShoeListings);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("shoeListingList", shoeListingList);

        //Number of pages total that will be listed at once in the pagination menu.  Keep an even number for current code configuration.
        int paginationMenuTotalVisible = 4;
        model.addAttribute("paginationMenuTotalVisible", paginationMenuTotalVisible);
        model.addAttribute("paginationMenuSplitSidesVisible", paginationMenuTotalVisible / 2);

        return "listings/listingSearch";

    }


    @PostMapping("/delete")
    public String deleteListing(@ModelAttribute("listing") ShoeListing shoeListing, Model model){
        String imageName = "listing_image_" + shoeListing.getId() + ".jpg";
        try {
            Path filePath = Paths.get(uploadDir, imageName);

            // Check if the file exists before deleting
            if (Files.exists(filePath)) {
                Files.delete(filePath);

            }
        } catch (IOException e) {

        }

        UserEntity user = shoeListing.getUserEntity();
        user.removeListing(shoeListing);
        shoeListingRepository.delete(shoeListing);
        return "redirect:../profile";
    }

}
