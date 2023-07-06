package org.launchcode.liftoff.shoefinder.services;

import org.launchcode.liftoff.shoefinder.data.ImageRepository;
import org.launchcode.liftoff.shoefinder.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public void storeImage(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Image image = new Image(fileName, file.getContentType(), file.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            // Handle exception
            //TODO:handle exception
        }
    }
}
