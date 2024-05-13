package com.akhil.jwt.controller;

import com.akhil.jwt.entity.User;
import com.akhil.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
public class ImageController {

    private static final String UPLOAD_DIR = "../images";

    @Autowired private UserService userService;

    @PostMapping({"/upload"})
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("userName") String userName) {
        User user = userService.findByUserName(userName).get();

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = file.getOriginalFilename();
            String newFileName = userName+"_"+fileName;
            userService.setUserImage(newFileName,userName);

            Path filePath = uploadPath.resolve(Objects.requireNonNull(newFileName));
            Files.write(filePath, file.getBytes());

            System.out.println("uploaded");
//            System.out.println(user.getUserLastName());
//            System.out.println(user.getImage());

            System.out.println(newFileName);

            System.out.println(user.getImage());
            return new ResponseEntity<>("{ \"status\": \"success\" }", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("error uploading" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) throws IOException {
        Path imagePath = Paths.get(UPLOAD_DIR).resolve(fileName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            System.out.println("getting image "+ imagePath);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(imagePath))
                    .body(resource);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
