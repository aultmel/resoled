
package org.launchcode.liftoff.shoefinder.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.launchcode.liftoff.shoefinder.data.ImageInfoRepository;
import org.launchcode.liftoff.shoefinder.models.ImageInfo;
import org.launchcode.liftoff.shoefinder.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


// NOT BEING USED BUT HERE FOR REFERENCE IN CASE WE ADDED DELETE IMAGE FUNCTION

@Controller
public class ImageController {

    private final StorageService storageService;
    private final ImageInfoRepository imageInfoRepository;

    public ImageController(StorageService storageService, ImageInfoRepository imageInfoRepository) {
        this.storageService = storageService;
        this.imageInfoRepository = imageInfoRepository;
    }

    @GetMapping("/images/new")
    public String newImage(Model model) {
        return "uploadForm";
    }

    @PostMapping("/images/upload")
    public String uploadImage(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            storageService.saveUserImage(file);

            message = "Uploaded the image successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }

        return "uploadForm";
    }

    @GetMapping("/images")
    public String getListImages(Model model) {
        List<ImageInfo> imageInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class, "getImage", path.getFileName().toString()).build().toString();

            return new ImageInfo(filename, url);
        }).collect(Collectors.toList());

        model.addAttribute("images", imageInfos);

        List<ImageInfo> imageInfoList = imageInfoRepository.findAll();

       ImageInfo anImageInfo = imageInfoList.get(imageInfoList.size()-1);

       String fileName = anImageInfo.getName();

//        if (path == null) {
//            return null;
//        }

//        String url = MvcUriComponentsBuilder
//                .fromMethodName(ImageController.class, "getImage", fileName).build().toString();
//
//        anImageInfo.setUrl(url);
//        imageInfoRepository.save(anImageInfo);

        model.addAttribute("anImage", anImageInfo);

        return "images";
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = storageService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/images/delete/{filename:.+}")
    public String deleteImage(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes) {
        try {
            boolean existed = storageService.delete(filename);

            if (existed) {
                redirectAttributes.addFlashAttribute("message", "Delete the image successfully: " + filename);
            } else {
                redirectAttributes.addFlashAttribute("message", "The image does not exist!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Could not delete the image: " + filename + ". Error: " + e.getMessage());
        }

        return "redirect:/images";
    }
}
