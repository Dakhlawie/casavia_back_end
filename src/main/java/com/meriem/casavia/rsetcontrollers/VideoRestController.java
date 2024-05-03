package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Image;
import com.meriem.casavia.entities.Video;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/video")
@CrossOrigin
public class VideoRestController {
    @Autowired
    VideoRepository videoRep;
    @Autowired
    HebergementRepository hebergementRep;
    @PostMapping("/save")
    public Video ajouterVideo(@RequestParam("video") MultipartFile file) throws IOException {
        try {
            Video video = new Video();
            video.setVideoContent(file.getBytes());
            return videoRep.save(video);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage(), e);
        }
    }
    @RequestMapping(value = "/getVideoHeber/{idHeber}" ,
            method = RequestMethod.GET)
    public Video getVideoHeber(@PathVariable("idHeber") Long idHeber)
            throws IOException {
        Hebergement h=hebergementRep.findById(idHeber).get();
        System.out.println(videoRep.getVideoByHebergement(h));
        return videoRep.getVideoByHebergement(h);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteVideo(@PathVariable("id") long id)
    {
        this.videoRep.deleteById(id);
    }



}
