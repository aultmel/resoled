package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;
import lombok.Builder;



//DEPRECATED DUE TO STORING IMAGES LOCALLY INSTEAD OF IN A DB
/*
>>>>>>> speck-nav:src/main/java/org/launchcode/liftoff/shoefinder/models/ImageDB.java
@Entity
@Table(name = "image")
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    @Column(name = "imageData", length = 2000)
    private byte[] imageData;

    @ManyToOne
    private ShoeListing listing;

    public Image() {
    }

    public Image(String fileName, String fileType, byte[] imageData) {
        this.imageData = imageData;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Long getId() {
        return id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
<<<<<<< HEAD:src/main/java/org/launchcode/liftoff/shoefinder/models/Image.java
}

}

 */
