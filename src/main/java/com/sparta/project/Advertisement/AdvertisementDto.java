package com.sparta.project.Advertisement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * AdvertisementDto는 광고 정보를 전달하기 위한 데이터 전송 객체
 * 주로 컨트롤러와 서비스 간의 데이터 전달에 사용
 */
@Getter
@Setter
public class AdvertisementDto {
    private Long id;

    @NotBlank
    @Size(min = 10, message = "Ad content must be at least 10 characters long")
    private String adContent;

    private Long viewCount;

    @NotNull(message = "Video ID cannot be null")
    private Long videoId;
}
