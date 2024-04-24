package com.collage.collageerp.Services;

import com.collage.collageerp.entity.FileData;
import com.collage.collageerp.repository.FileDataRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import com.collage.collageerp.DAO.StudentDAO;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StorageService {


    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private StaffServiceImpl staffService;

    @Autowired
    private StudentServiceImpl studentService;

    //this is the folder where you want to store the image
    private final String FOLDER_PATH="C:\\Users\\acer\\Documents\\MyProject\\Angular Project\\collage-erp\\src\\assets\\images\\Profile-image\\";
    //C:\Users\acer\Documents\MyProject\Springboot Project\image-uploader-downloader\file-storage\target\images



    //upload or store image in the file System
    public String uploadImageToFileSystem(
        MultipartFile file,
        String role,
        Integer userId
    ) throws IOException {

        String originalName = file.getOriginalFilename();
        log.info("Original name is : {}",originalName);
        String[] imageExtention = originalName.split("\\.");
        String newName = role + "-" + userId + "." + imageExtention[imageExtention.length - 1];
        String filePath=FOLDER_PATH + newName;
        log.info(FOLDER_PATH);
        System.out.println(FOLDER_PATH);
        log.info(newName);
        if(role.equals("staff")){
            staffService.setStaffProfilePic(userId, newName);
        } else if (role.equals("student")) {
            studentService.setStudentProfilePic(userId, newName);
        }

        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(newName)
                .role(role)
                .userId(userId)
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    //download (get) image from the file system
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    //download image by userId
    public byte[] downloadImageByUserId(Integer userId) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByUserId(userId);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }


    public ResponseEntity<List<FileData>> getAllImage() throws IOException {
        List<FileData> fileDatas = fileDataRepository.findAll();
        return ResponseEntity.of(Optional.of(fileDatas));
    }

    @Transactional
    public ResponseEntity<FileData> deleteImageById(Long id) {
        try {
            log.info("Id from Service : {}", id );
            FileData fileData = fileDataRepository.findById(id);
            log.info("File data is : {}", fileData);
            fileDataRepository.deleteById(id);
            log.info("Deleted Data is : {}",  fileDataRepository.deleteById(id));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            log.error("Internal Error is : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public String updateImageById(MultipartFile file, Long id, String role, Integer userId) throws IOException {
        String originalName = file.getOriginalFilename();
        log.info("Original name is : {}",originalName);
        String[] imageExtention = originalName.split("\\.");
        String newName = role + "-" + userId + "." + imageExtention[imageExtention.length - 1];
        String filePath=FOLDER_PATH + newName;
        log.info(FOLDER_PATH);
        System.out.println(FOLDER_PATH);

        FileData fileData=fileDataRepository.save(FileData.builder()
            .id(id)
            .name(newName)
            .type(file.getContentType())
            .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file change successfully : " + filePath;
        }
        return null;

    }
}
