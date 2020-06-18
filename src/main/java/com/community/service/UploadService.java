package com.community.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {

    public String uploadImage(MultipartFile file, String imgageDir) {
        Path rootLocation = Paths.get("/images/");

        String[] filepaths = file.getOriginalFilename().split("\\.");
        String generatedImageName = "";
        if (filepaths.length > 1) {
            //Using UUID to generate a random image name
            generatedImageName = UUID.randomUUID().toString() + "." + filepaths[filepaths.length - 1];
        }
        try {
            byte[] bytes = file.getBytes();
            Files.write(Path.of(imgageDir + generatedImageName), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootLocation.resolve(generatedImageName).toString();
    }
}
