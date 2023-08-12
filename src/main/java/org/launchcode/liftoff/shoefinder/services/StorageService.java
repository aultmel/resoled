package org.launchcode.liftoff.shoefinder.services;

import org.apache.commons.io.FilenameUtils;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.ImageInfo;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Service
public class StorageService {

    private final Path root = Paths.get("./uploads");

    private final UserRepository userRepository;


    public StorageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }


    public void save(MultipartFile file) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);


        // Checks file size
        final long MAX_FILE_SIZE = 3 * 1024 * 1024; // 3MB
        if (file.getSize() > MAX_FILE_SIZE) {
//            throw new Exception("File size is too large.");
//                return "File size is too large.";
        }

        ImageInfo imageInfo = new ImageInfo();

//        imageInfo.setUrl();
//        imageInfo.setName();

      //WORKING HERE

        // Checks file extension type
        String fileName = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(fileName);

        if (fileExtension != null &&
                (fileExtension.equals("png")
                        || fileExtension.equals("jpg")
                        || fileExtension.equals("jpeg")
                        || fileExtension.equals("gif"))) {
            //FILE GOOD
        } else {
//            throw new Exception("File must be png, jpg, jpeg, or gif");

//           return "File must be png, jpg, jpeg, or gif";
        }



        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }


    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public boolean delete(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }


    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
