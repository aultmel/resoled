package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.BrandRepository;
import org.launchcode.liftoff.shoefinder.models.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SearchController {

    private BrandRepository brandRepository;

    @GetMapping("/search")
    public String search(Model model){
        List<Brand> popularBrands = brandRepository.findPopularBrands();
        List<Brand> topFiveBrands = popularBrands.subList(0, 4);
        model.addAttribute("topBrands", topFiveBrands);
        return "search";
    }


}