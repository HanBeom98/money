package com.sparta.project.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUploadController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam String bucketName, @RequestParam String keyName, @RequestParam String filePath) {
        String fileUrl = s3Service.uploadFileAndGetUrl(bucketName, keyName, filePath);
        if (fileUrl != null) {
            return "File uploaded successfully. File URL: " + fileUrl;
        } else {
            return "File upload failed.";
        }
    }
}
