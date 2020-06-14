package com.community.controller;

import com.community.dto.ImageDTO;
import com.community.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ImageController {

    @Value("${web.image.dir}")
    private String imageDir;

    @Autowired
    private UploadService uploadService;

    @RequestMapping("/image/upload")
    @ResponseBody
    public ImageDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest  multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");

        String imageUrl = uploadService.uploadImage(file, imageDir);

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setSuccess(1);
        imageDTO.setMessage("Uploaded successed");
        imageDTO.setUrl(imageUrl);

        return imageDTO;
    }
}
