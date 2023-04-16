package com.cloud.test.file.service;

import com.cloud.test.file.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileUploadUtil fileUploadUtil;

    public String uploadFile(MultipartFile multipartFile) {
        return fileUploadUtil.uploadFile(multipartFile);
    }
}
