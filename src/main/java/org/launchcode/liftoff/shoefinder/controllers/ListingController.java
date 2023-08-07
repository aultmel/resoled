package org.launchcode.liftoff.shoefinder.controllers;


import org.launchcode.liftoff.shoefinder.data.ImageRepository;
import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.models.ImageLocal;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.dto.CreateListingDTO;

import org.launchcode.liftoff.shoefinder.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

import static org.launchcode.liftoff.shoefinder.constants.ListingConstants.GENDER_LIST;
import static org.launchcode.liftoff.shoefinder.constants.ListingConstants.SIZE_LIST;


@Controller
@RequestMapping("listings")
public class ListingController {

    private final ListingService listingService;
    private final ShoeListingRepository shoeListingRepository;

    // Constructor to inject ListingService and ShoeListingRepository dependencies.

    public ListingController(ListingService listingService, ShoeListingRepository shoeListingRepository) {
        this.listingService = listingService;
        this.shoeListingRepository = shoeListingRepository;

    }
    // Handler method to display all shoe listings.

    @GetMapping
    public String displayAllListings(Model model) {
        model.addAttribute("title", "All Listings");
        model.addAttribute("allListings", shoeListingRepository.findAll());
        return "/listings/listings";

    }
    // Handler method to display details of a specific shoe listing.

    @GetMapping("details")
    public String displayListingDetails(@RequestParam Long listingId, Model model) {
        Optional<ShoeListing> result = shoeListingRepository.findById(listingId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid ShoeListing ID: " + listingId);
        } else {
            ShoeListing shoeListing = result.get();
            model.addAttribute("title", shoeListing.getId());
            model.addAttribute("shoeListing", shoeListing);
        }
        return "/listings/listing";
    }
    // Handler method to display the shoe listing creation form.

    @GetMapping("create")
    public String showListingForm(Model model) {

        model.addAttribute("genderList", GENDER_LIST);
        model.addAttribute("sizeList", SIZE_LIST);
        model.addAttribute("createListingDTO", new CreateListingDTO());
        return "/listings/create";
    }

    // Handler method to create a new shoe listing.

    @PostMapping("create")
    public String createListing(@ModelAttribute("createListingDTO") CreateListingDTO createListingDTO, RedirectAttributes redirectAttributes
        ,@RequestParam("imageFiles") MultipartFile[] files, Model model
    ) {
        redirectAttributes.addFlashAttribute("message", "Shoe Listing Created");
        listingService.saveListing(createListingDTO, files);


        // Redirect to a success page
        return "redirect:../home";
    }
}
