package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.BrandRepository;
import org.launchcode.liftoff.shoefinder.models.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/search")
    public String search(Model model){
        List<Object[]> popularBrandsData = brandRepository.findPopularBrands();
        List<String> popularBrands = new ArrayList<>();
        for (Object[] brand : popularBrandsData) {
            String brandName = (String) brand[0];
            popularBrands.add(brandName);
        }
        model.addAttribute("topBrands", popularBrands);
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

        return "search";
    }


}