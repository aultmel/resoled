package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/listing")
public class ListingController {

    @Autowired
    private ShoeListingRepository listingRepository;

    @GetMapping("create")
    public String showListingForm(Model model) {
        model.addAttribute("shoeListing", new ShoeListing());
        return "/listing/listing-form";
    }

    //will need dto to transfer userEntity info along with form data to create populate Listing
    @PostMapping("create")
    public String createListing(@ModelAttribute("listing") ShoeListing shoeListing,
                                @RequestParam("photoFile") MultipartFile photoFile) {





        // Redirect to a success page
        return "redirect:/success";
    }
}