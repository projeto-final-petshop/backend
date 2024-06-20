package br.com.finalproject.petconnect.photo.controllers;

import br.com.finalproject.petconnect.photo.services.PhotoService;
import br.com.finalproject.petconnect.photo.entities.Photo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/photos")
@RestController
@AllArgsConstructor
//@CrossOrigin(
//        maxAge = 36000,
//        allowCredentials = "true",
//        value = {"http://localhost:4200", "http://localhost:9090"},
//        allowedHeaders = {"Authorization", "Content-Type"},
//        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE})
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @GetMapping("/{id}")
    public Photo getPhotoById(@PathVariable Long id) {
        return photoService.getPhotoById(id);
    }

    @PostMapping
    public Photo savePhoto(@RequestBody Photo photo) {
        return photoService.savePhoto(photo);
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
    }

}
