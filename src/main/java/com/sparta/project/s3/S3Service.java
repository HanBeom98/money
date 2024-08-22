package com.sparta.project.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import org.springframework.stereotype.Service;

@Service
public class S3Service {

    private final S3Client s3;

    public S3Service() {
        this.s3 = S3Client.builder()
                .region(Region.AP_NORTHEAST_2)  // 서울 리전
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    // S3에 이미 존재하는 파일의 URL을 가져오는 메서드
    public String getExistingFileUrl(String bucketName, String keyName) {
        try {
            // 이미 S3에 존재하는 파일의 URL 생성
            String fileUrl = String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", bucketName, keyName);

            System.out.println("File URL: " + fileUrl);
            return fileUrl;
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return null;
        }
    }
}
