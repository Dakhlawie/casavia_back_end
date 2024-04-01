package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Chambre;
import com.meriem.casavia.entities.Image;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.services.ChambreService;
import com.meriem.casavia.services.ImageService;
import com.meriem.casavia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")

public class ImageRestController {
    @Autowired
    ImageService imageService ;
    @Autowired
    UserService userService;
    @Autowired
    ChambreService chambreserv;

    @PostMapping( "/upload" )
    public Image uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uplaodImage(file);
    }
    @GetMapping("/get/info/{id}")
    public byte[] getImageData(@PathVariable("id") Long id) throws IOException {
        Image image = imageService.getImageDetails(id);
        return image.getImage();
    }

    @GetMapping( "/load/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException
    {
        return imageService.getImage(id);
    }


    @DeleteMapping ( "/delete/{id}" )
    public void deleteImage(@PathVariable("id") Long id){
        imageService.deleteImage(id);
    }
    @PutMapping ("/update")
    public Image UpdateImage(@RequestParam("image")MultipartFile file) throws IOException {
        return imageService.uplaodImage(file);
    }

    @PostMapping("/uplaodImageHeber/{idHeber}" )
    public Image uploadMultiImages(@RequestParam("image")MultipartFile file,
                                   @PathVariable("idHeber") Long idHeber)
            throws IOException {
        return imageService.uplaodImageHeber(file,idHeber);
    }
    @RequestMapping(value = "/getImagesHeber/{idHeber}" ,
            method = RequestMethod.GET)
    public List<Image> getImagesHeber(@PathVariable("idHeber") Long idHeber)
            throws IOException {
        return imageService.getImagesParHeber(idHeber);
    }
    @RequestMapping(value = "/uploadFS/{id}" , method = RequestMethod.POST)
    public void uploadImageFS(@RequestParam("image") MultipartFile
                                      file,@PathVariable("id") Long id) throws IOException {
        User u = userService.getUser(id);
        u.setImage_path(id+".jpg");

        Files.write(Paths.get(System.getProperty("user.home")+"/images/"+u.getImage_path())
                , file.getBytes());
        userService.ajouterUser(u);

    }
    @RequestMapping(value = "/loadfromFS/{id}" ,
            method = RequestMethod.GET,
            produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageFS(@PathVariable("id") Long id) throws IOException {

        User user = userService.getUser(id);
        return
                Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/images/"+user.getImage_path()));
    }
    @RequestMapping(value = "/uploadFSChambre/{id}" , method = RequestMethod.POST)
    public void uploadImageFSChambre(@RequestParam("image") MultipartFile
                                      file,@PathVariable("id") Long id) throws IOException {
        Chambre u = chambreserv.getChambreById(id);
        u.setImage_path(id+".jpg");

        Files.write(Paths.get(System.getProperty("user.home")+"/rooms/"+u.getImage_path())
                , file.getBytes());
        chambreserv.ajouterChambre(u);

    }
    @RequestMapping(value = "/loadfromFSChambre/{id}" ,
            method = RequestMethod.GET,
            produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageFSChambre(@PathVariable("id") Long id) throws IOException {

        Chambre c = chambreserv.getChambreById(id);
        return
                Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/rooms/"+c.getImage_path()));
    }



}
