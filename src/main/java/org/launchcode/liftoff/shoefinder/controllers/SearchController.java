package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.BrandRepository;
import org.launchcode.liftoff.shoefinder.data.StyleRepository;
import org.launchcode.liftoff.shoefinder.models.Brand;
;
import org.launchcode.liftoff.shoefinder.models.Size;
import org.launchcode.liftoff.shoefinder.models.Style;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;

import static org.launchcode.liftoff.shoefinder.constants.ListingConstants.CONDITION_LIST;

@Controller
public class SearchController {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private StyleRepository styleRepository;

    @GetMapping("/search")
    public String search(Model model){

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
        List<String> shoeSizeList = Size.getAllSizes();
        model.addAttribute("shoeSizes", shoeSizeList);

        //Create list of conditions for the model
        List<String> conditionList = CONDITION_LIST;
        model.addAttribute("conditions", conditionList);

        return "search";
    }


}