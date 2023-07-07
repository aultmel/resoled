//package org.launchcode.liftoff.shoefinder.controllers;
//
//import org.launchcode.liftoff.shoefinder.models.Image;
//import org.launchcode.liftoff.shoefinder.services.ImageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Optional;
//
//@Controller
//public class ImageController {
//    @Autowired
//    ImageService imageService;
//
//    @GetMapping("/upload")
//    public String displayUploadForm(Model model){
//        model.addAttribute("imageFile", new Image());
//        return "/image/imageUploadPlaceholder";
//    }
//
//
//    @PostMapping("/upload")
//    public String uploadImage(@RequestParam("imageFile") MultipartFile file) {
//        imageService.storeImage(file);
//        return "/image/imageUploadPlaceholder";
//    }
//
//    @GetMapping("/image/{id}")
//    public String showImage(@PathVariable("id") Long id, Model model) {
//
//        Optional<Image> image = imageService.getImageById(id);
//        if (image.isPresent()) {
//            model.addAttribute("image", image.get());
//            return "image/";
//        } else {
//            return "error"; // or handle the error in a different way
//        }
//    }
//}