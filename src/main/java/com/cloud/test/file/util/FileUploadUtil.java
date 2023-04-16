package com.cloud.test.file.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class FileUploadUtil {

    private final AmazonS3Client amazonS3Client;
    private final String buketName;

    public FileUploadUtil(AmazonS3Client amazonS3Client,
                          @Value("${cloud.aws.s3.bucket}") String buketName) {
        this.amazonS3Client = amazonS3Client;
        this.buketName = buketName;
    }

    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    buketName,
                    storeFilename,
                    file.getInputStream(),
                    metadata).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3Client.putObject(putObjectRequest);
            return amazonS3Client.getUrl(buketName, storeFilename).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String fileUrl) {
        int point = fileUrl.indexOf('/', 10) + 1;
        String fileKey = fileUrl.substring(point);
        amazonS3Client.deleteObject(buketName, fileKey);
        log.info("success delete file = {}", fileKey);
    }

    private String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID() + ext;
    }

    private String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }
}
