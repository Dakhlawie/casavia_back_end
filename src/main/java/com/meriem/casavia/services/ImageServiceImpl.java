package com.meriem.casavia.services;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Image;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.ImageRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.meriem.casavia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;;


@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    HebergementService hebergementService;
    @Autowired
    HebergementRepository hebergementRepository;
    @Autowired
    UserRepository userRep;
    @Override
    public Image uplaodImage(MultipartFile file) throws IOException {
 /*Ce code commenté est équivalent au code utilisant le design pattern Builder
 * Image image = new Image(null, file.getOriginalFilename(),
 file.getContentType(), file.getBytes(), null);
 return imageRepository.save(image);*/
        return imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes()).build() );
    }
    @Override
    public Image getImageDetails(Long id) throws IOException{
        final Optional<Image> dbImage = imageRepository. findById (id);
        return Image.builder()
                .idImage(dbImage.get().getIdImage())
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(dbImage.get().getImage()).build() ;
    }
    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException{
        final Optional<Image> dbImage = imageRepository. findById (id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(dbImage.get().getImage());
    }
    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image uplaodImageHeber(MultipartFile file, Long idhebergement) throws IOException {
        Hebergement h = new Hebergement();
        h.setHebergement_id(idhebergement);
        return imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes())
                .hebergement(h).build() );
    }

    @Override
    public List<Image> getImagesParHeber(Long heberId) {
        Hebergement h = hebergementRepository.findById(heberId).get();
        return h.getImages();

    }


}

