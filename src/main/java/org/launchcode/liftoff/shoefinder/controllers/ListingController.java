package org.launchcode.liftoff.shoefinder.controllers;

import jakarta.validation.Valid;
import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.dto.CreateListingDTO;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;
import org.launchcode.liftoff.shoefinder.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/listing")
public class ListingController {

//    @Autowired
//    private ShoeListingRepository listingRepository;



    private final ListingService listingService;

    public ListingController (ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/create")
    public String showListingForm(Model model) {
        model.addAttribute("createListingDTO", new CreateListingDTO());
        model.addAttribute("shoeListing", new ShoeListing());
        return "/listing/create";
    }

    //will need dto to transfer userEntity info along with form data to create populate Listing
    @PostMapping("/create")
    public String createListing(@Valid @ModelAttribute("createListingDTO") CreateMessageDTO createMessageDTO, BindingResult bindingResult) {
        {


//        @RequestParam("photoFile") MultipartFile photoFile)


            // Redirect to a success page
            return "redirect:/success";
        }
    }}

//    @GetMapping("/listing")
//    public String showListingForm(Model model) {
//        model.addAttribute("listing", new ShoeListing());
//        return "listing-form";
//    }
//
//    @PostMapping("/listing")
//    public String createListing(@ModelAttribute("listing") ShoeListing shoeListing,
//                                @RequestParam("photoFile") MultipartFile photoFile) {
//        try {
//            if (!photoFile.isEmpty()) {
//                // Get the bytes of the photo file
//                byte[] photoBytes = photoFile.getBytes();
//
//                // Set the photo bytes to the listing
//                shoeListing.setPhoto(photoBytes);
//            }
//
//            // Save the listing to the database
//            listingRepository.save(shoeListing);
//        } catch (Exception e) {
//            // Handle any exceptions, e.g., return to the form with an error message
//            return "redirect:/listing?error=upload";
//        }
//
//        // Redirect to a success page
//        return "redirect:/success";
//    }
//}