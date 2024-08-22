package com.sparta.project.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class S3Service {

    private final S3Client s3;

    public S3Service() {
        this.s3 = S3Client.builder()
                .region(Region.AP_NORTHEAST_2)  // 서울 리전
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public String uploadFileAndGetUrl(String bucketName, String keyName, String filePath) {
        try {
            // S3에 파일 업로드
            s3.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .build(),
                    Paths.get(filePath));

            // 파일의 S3 URL 생성
            String fileUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, keyName);

            System.out.println("File uploaded successfully. File URL: " + fileUrl);
            return fileUrl;
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return null;
        }
    }
}
