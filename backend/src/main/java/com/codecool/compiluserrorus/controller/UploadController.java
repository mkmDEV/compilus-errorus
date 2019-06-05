package com.codecool.compiluserrorus.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
public class UploadController {
    private static final String UPLOADED_FOLDER = System.getenv("IMAGE_PATH");
    @PostMapping("/upload")
    public void saveImage(@RequestPart("file") MultipartFile file) {
        try {
            //byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER);
            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
            //Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
