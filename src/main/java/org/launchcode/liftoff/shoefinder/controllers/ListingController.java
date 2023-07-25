package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
@RequestMapping("/listings")
public class ListingController {

    @Autowired
    private ShoeListingRepository shoeListingRepository;

    @GetMapping
    public String displayAllListings(Model model) {
        model.addAttribute("title", "All Listings");
        model.addAttribute("allListings", shoeListingRepository.findAll());

        return "listings";
    }

    @GetMapping("details")
    public String displayListingDetails(@RequestParam Integer listingId, Model model) {
        Optional<ShoeListing> result = shoeListingRepository.findById(listingId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid ShoeListing ID: " + listingId);
        } else {
            ShoeListing shoeListing = result.get();
            model.addAttribute("title", shoeListing.getId());
            model.addAttribute("shoeListing", shoeListing);
        }
        return "listing";
    }

    @GetMapping("create")
    public String showListingForm(Model model) {
        //model.addAttribute("createListingDto", new CreateListingDTO());
        model.addAttribute("shoeListing", new ShoeListing());
        return "create";
    }

    //will need dto to transfer userEntity info along with form data to create populate Listing
    @PostMapping("create")
    public String createListing(@ModelAttribute("listing") ShoeListing shoeListing,
                                @RequestParam("photoFile") MultipartFile photoFile) {





        // Redirect to a success page
        return "redirect:/success";
    }
}