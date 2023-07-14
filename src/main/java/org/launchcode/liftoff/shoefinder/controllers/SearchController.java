package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.BrandRepository;
import org.launchcode.liftoff.shoefinder.data.StyleRepository;
import org.launchcode.liftoff.shoefinder.models.Brand;
import org.launchcode.liftoff.shoefinder.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private StyleRepository styleRepository;

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String searchTerm,
                         @RequestParam(required = false) List<Brand> brands,
                         @RequestParam(required = false) List<Size> sizes,
                         @RequestParam(required = false) List<Style> styles,
                         @RequestParam(required = false) List<Condition> conditions,
                         Model model){

        //Grab 5 most recurring brands from database
        List<Object[]> topBrandsData = brandRepository.findPopularBrands();
        List<String> topBrands = new ArrayList<>();

        //For each brand object, grab the name and store in topBrands
        for (Object[] brand : topBrandsData) {
            String brandName = (String) brand[0];
            topBrands.add(brandName);
        }
        //Add topBrands to the model for search.html
        model.addAttribute("topBrands", topBrands);

        //Grab 5 most recurring styles from database
        List<Object[]> topStylesData = styleRepository.findPopularStyles();
        List<String> topStyles = new ArrayList<>();

        //For each style object, grab the name and store in topStyles
        for (Object[] style : topStylesData) {
            String styleName = (String) style[0];
            topStyles.add(styleName);
        }
        //Add topStyles to the model for search.html
        model.addAttribute("topStyles", topStyles);

        //Create list of shoe sizes for the model
        List<String> shoeSizes = new ArrayList<>();
        shoeSizes.add("5");
        shoeSizes.add("6");
        shoeSizes.add("7");
        shoeSizes.add("8");
        shoeSizes.add("9");
        shoeSizes.add("10");
        shoeSizes.add("11");
        shoeSizes.add("12");
        shoeSizes.add("13");
        model.addAttribute("shoeSizes", shoeSizes);

        //Create list of conditions for the model
        List<String> conditions = new ArrayList<>();
        conditions.add("New");
        conditions.add("Excellent");
        conditions.add("Good");
        conditions.add("Acceptable");
        model.addAttribute("conditions", conditions);

        return "search";
    }
}