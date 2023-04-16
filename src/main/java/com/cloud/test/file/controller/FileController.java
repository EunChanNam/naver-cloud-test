package com.cloud.test.file.controller;

import com.cloud.test.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/api/upload")
    public String uploadFile(@RequestPart MultipartFile file) {

        return fileService.uploadFile(file);
    }
}
