package com.meriem.casavia.services;

import java.io.IOException;
import java.util.List;

import com.meriem.casavia.entities.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image uplaodImage(MultipartFile file) throws IOException;
    Image getImageDetails(Long id) throws IOException;
    ResponseEntity<byte[]> getImage(Long id) throws IOException;
    void deleteImage(Long id) ;
    Image uplaodImageHeber(MultipartFile file,Long idhebergement) throws IOException;
    List<Image> getImagesParHeber(Long heberId);

}