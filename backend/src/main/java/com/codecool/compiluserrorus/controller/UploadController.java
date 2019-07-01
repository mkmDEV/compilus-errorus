package com.codecool.compiluserrorus.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
public class UploadController {

    private String imagePath = System.getenv("IMAGE_PATH");

    @PostMapping("/upload")
    public void saveImage(@RequestPart("file") MultipartFile file) {
        try {
            Path path = Paths.get(imagePath);
            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/image-resource/{image}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getFile(@PathVariable("image") String fileName) {
        return new FileSystemResource(imagePath + fileName);
    }

    @GetMapping("/teapot")
    public ResponseEntity<Object> imATeapot() {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
    }
}
