package org.launchcode.liftoff.shoefinder.services;

import org.launchcode.liftoff.shoefinder.Util.ImageUtils;
import org.launchcode.liftoff.shoefinder.data.ImageRepository;
import org.launchcode.liftoff.shoefinder.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public String uploadImage(MultipartFile file) throws IOException {

        //changing first constructor variable from file.getOriginalFilename() to dbcheck
        Image image= imageRepository.save(new Image(dbNameCheck(file.getOriginalFilename()), file.getContentType(), ImageUtils.compressImage(file.getBytes())));

        if(image!=null){
            return "file uploaded: " + file.getOriginalFilename();
        }

        return null;
    }

    public byte[] downloadImage(String filename){
        Optional<Image> dbImageFile = imageRepository.findByFileName(filename);
        byte[] images = ImageUtils.decompressImage(dbImageFile.get().getImageData());
        return images;
    }

    //checks the filenames in the db for duplicate images to fine the number to affix for a unique filename,
    //returns fileName with said number affixed
    public String dbNameCheck(String filename){
        int count=1;
        while(imageRepository.findByFileName(filename).isPresent()){
            filename += count;
        }
        return filename;
    }









    /*public void storeImage(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Image image = new Image(fileName, file.getContentType(), file.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            // Handle exception
            //TODO:handle exception
        }
    }

    public Optional<Image> getImageById(Long id){
        Optional<Image> image = imageRepository.findById(id);
        return image;
    }
*/
}
