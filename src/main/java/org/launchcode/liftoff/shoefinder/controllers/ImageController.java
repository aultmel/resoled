
package org.launchcode.liftoff.shoefinder.controllers;

//import org.launchcode.liftoff.shoefinder.models.Image;
//import org.launchcode.liftoff.shoefinder.services.ImageService;
import org.launchcode.liftoff.shoefinder.data.ImageRepository;
import org.launchcode.liftoff.shoefinder.models.ImageLocal;
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

    //DEPRECATED TO STORE IMAGES LOCALLY

//    @Autowired
//    ImageService imageService;

    @Autowired
    ImageRepository imageRepository;
    @GetMapping("upload")
    public String displayUploadForm(Model model){
        return "/image/imageUploadPlaceholder";
    }
    @PostMapping("upload")
    public String uploadImage(@RequestParam("imageFiles") MultipartFile[] files, Model model) throws IOException {
        for(MultipartFile imageFile: files) {
            ImageLocal imageLocal = new ImageLocal();
            imageLocal.setImageFile(imageFile);
            imageRepository.save(imageLocal);
            imageLocal.saveImageLocally(files);
        }



        return "/image/uploadSuccess";
    }




    //Not configured, also not sure if useable for our application. (Why would we want to show one specific image only?)
    /*
    @GetMapping("/{filename}")
    public String downloadImage(@PathVariable String filename, Model model){
        byte[] imageData = imageService.downloadImage(filename);
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        model.addAttribute("imageData", base64Image);
        return "/image/display";
    }
    */

}
