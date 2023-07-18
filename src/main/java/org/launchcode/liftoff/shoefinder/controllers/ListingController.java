package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller("/listing")
public class ListingController {

    @Autowired
    private ShoeListingRepository listingRepository;

    @GetMapping("create")
    public String showListingForm(Model model) {
        model.addAttribute("listing", new ShoeListing());
        return "/listing/listing-form";
    }

    //will need dto to transfer userEntity info along with form data to create populate Listing
    @PostMapping("create")
    public String createListing(@ModelAttribute("listing") ShoeListing shoeListing,
                                @RequestParam("photoFile") MultipartFile photoFile) {
        //TODO:Need to handle multiple image files before worrying about this
        /*try {
            if (!photoFile.isEmpty()) {
                // Get the bytes of the photo file
                byte[] photoBytes = photoFile.getBytes();

                // Set the photo bytes to the listing
                shoeListing.setImages(photoBytes);
            }

            // Save the listing to the database
            listingRepository.save(shoeListing);
        } catch (Exception e) {
            // Handle any exceptions, e.g., return to the form with an error message
            return "redirect:/listing?error=upload";
        }
        */
        // Redirect to a success page
        return "redirect:/success";
    }
}