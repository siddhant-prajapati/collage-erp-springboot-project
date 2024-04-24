package com.collage.collageerp.Controllers;

import com.collage.collageerp.entity.FileData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.collage.collageerp.Services.StorageService;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("image")
@CrossOrigin("http://localhost:4200")
public class ImageController {
  @Autowired
  private StorageService service;


  //for uploading image in the folder
  @CrossOrigin("*")
  @PostMapping("/fileSystem/{role}/{id}")
  public ResponseEntity<?> uploadImageToFIleSystem(
      @RequestParam("image") MultipartFile file,
      @PathVariable("role") String role,
      @PathVariable("id") Integer userId
  ) throws IOException {
    log.info("Inside post");
    System.out.println(file);
    String uploadImage = service.uploadImageToFileSystem(file, role, userId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(uploadImage);
  }

  // Add this method to handle OPTIONS requests
  @RequestMapping(value = "/fileSystem/{role}/{id}", method = RequestMethod.OPTIONS)
  public ResponseEntity<?> handleOptionsRequest() {
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/fileSystem/{id}")
  public ResponseEntity<?> updateImageUsingId(
      @RequestParam("image") MultipartFile file,
      @RequestParam("role") String role,
      @RequestParam("userId") Integer userId,
      @PathVariable("id") Long id
  ) throws IOException{
    String updatedImage = service.updateImageById(file, id, role, userId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(updatedImage);
  }

  //for getting in the folder using image name
  @GetMapping("/fileSystem/{fileName}")
  public ResponseEntity<?> downloadImageFromFileSystem(
      @PathVariable String fileName
  ) throws IOException {
    byte[] imageData=service.downloadImageFromFileSystem(fileName);
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.valueOf("image/png"))
        .body(imageData);

  }

  //getting image using userId
  @GetMapping("/fileById/{userId}")
  public ResponseEntity<?> downloadImageUsingUserId(
      @PathVariable Integer userId
  ) throws IOException {
    byte[] imageData=service.downloadImageByUserId(userId);
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.valueOf("image/png"))
        .body(imageData);
  }



  //for getting all image
  @GetMapping("/fileSystem")
  public ResponseEntity<List<FileData>> getAllImageFromFolder() throws IOException {
    return service.getAllImage();
  }

  //for deleting unwanted images using id
  @DeleteMapping("/fileSystem/{id}")
  public ResponseEntity<FileData> deleteImageFromFolder(@PathVariable("id") Long id){
    log.info("Id is : {}", id);
    return service.deleteImageById(id);
  }

}
