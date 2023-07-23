package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.ShoeListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListingsController {
    @Autowired
    private ShoeListingRepository shoeListingRepository;

    @GetMapping("/listings")
    public String filterListings (@RequestParam(required = false) String searchTerm,
                                  @RequestParam(required = false) List<String> brands,
                                  @RequestParam(required = false) List<String> sizes,
                                  @RequestParam(required = false) List<String> styles,
                                  @RequestParam(required = false) List<String> conditions,
                                  Model model) {


        return "listings";
    }
}
