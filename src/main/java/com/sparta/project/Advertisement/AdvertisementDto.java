package com.sparta.project.Advertisement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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
