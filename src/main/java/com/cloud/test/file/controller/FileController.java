package com.cloud.test.file.controller;

import com.cloud.test.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/api/upload")
    public String uploadFile(@RequestPart MultipartFile file) {

        return fileService.uploadFile(file);
    }

    @DeleteMapping("/api/delete")
    public String deleteFile(@RequestParam String fileUrl) {
        fileService.deleteFile(fileUrl);
        return "success";
    }
}
