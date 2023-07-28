package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.dto.CreateListingDTO;
import org.launchcode.liftoff.shoefinder.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("listings")
public class ListingController {


    private ShoeListingRepository shoeListingRepository;

    private final ListingService listingService;

    public ListingController(ShoeListingRepository shoeListingRepository, ListingService listingService) {
        this.shoeListingRepository = shoeListingRepository;
        this.listingService = listingService;
    }

    @GetMapping
    public String displayAllListings(Model model) {
        model.addAttribute("title", "All Listings");
        model.addAttribute("allListings", shoeListingRepository.findAll());
        return "/listings/listings";

    }

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

    @GetMapping("/create")
    public String showListingForm(Model model) {

        model.addAttribute("createListingDTO", new CreateListingDTO());
        return "/listings/create";
    }

//    //will need dto to transfer userEntity info along with form data to create populate Listing
    @PostMapping("create")
    public String createListing(@ModelAttribute("createListingDTO") CreateListingDTO createListingDTO) {
//        @RequestParam("photoFile") MultipartFile photoFile

        listingService.saveListing(createListingDTO);

        // Redirect to a success page
        return "redirect:/success";
    }
}



