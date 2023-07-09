package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.models.Image;
import org.launchcode.liftoff.shoefinder.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller("/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @GetMapping("upload")
    public String displayUploadForm(Model model){
        return "/image/imageUploadPlaceholder";
    }

    @PostMapping("upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        imageService.uploadImage(file);
        return "/image/uploadSuccess";
    }

    //Not configured, also not sure if useable for our application. (Why would we want to show one specific image only?)
    @GetMapping("/{filename}")
    public String downloadImage(@PathVariable String filename){
        byte[] imageData = imageService.downloadImage(filename);
        return "";
    }





    /*




    @PostMapping("/upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile file) {
        imageService.storeImage(file);
        return "redirect:/";
    }

    @GetMapping("/image/{id}")
    public String showImage(@PathVariable("id") Long id, Model model) {

        Optional<Image> image = imageService.getImageById(id);
        if (image.isPresent()) {
            model.addAttribute("image", image.get());
            return "image/";
        } else {
            return "error"; // or handle the error in a different way
        }
    }

     */
}