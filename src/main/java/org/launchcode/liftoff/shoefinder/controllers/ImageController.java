
package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.models.Image;
import org.launchcode.liftoff.shoefinder.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @GetMapping("upload")
    public String displayUploadForm(Model model){
        return "/image/imageUploadPlaceholder";
    }

    @PostMapping("upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile file, Model model) throws IOException {
        imageService.uploadImage(file);
        //model.addAttribute("images", imageService)
        //return "/image/uploadSuccess";
        return "redirect:/" + file.getOriginalFilename();
    }

    //Not configured, also not sure if useable for our application. (Why would we want to show one specific image only?)
    @GetMapping("/{filename}")
    public String downloadImage(@PathVariable String filename, Model model){
        byte[] imageData = imageService.downloadImage(filename);
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        model.addAttribute("imageData", base64Image);
        return "/image/display";
    }



}