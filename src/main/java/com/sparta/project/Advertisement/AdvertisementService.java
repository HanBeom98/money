package com.sparta.project.Advertisement;

import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private VideoRepository videoRepository;  // VideoRepository 주입

    // 모든 광고를 조회하여 DTO 리스트로 반환
    public List<AdvertisementDto> findAllAdvertisements() {
        return advertisementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 광고를 저장하는 메서드, DTO를 엔티티로 변환 후 저장
    public AdvertisementDto saveAdvertisement(AdvertisementDto advertisementDto) {
        Advertisement advertisement = convertToEntity(advertisementDto);
        Advertisement savedAd = advertisementRepository.save(advertisement);
        return convertToDto(savedAd);
    }

    // ID로 광고를 조회하여 DTO로 반환
    public AdvertisementDto findAdvertisementById(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Advertisement not found"));
        return convertToDto(advertisement);
    }

    // 광고 조회수를 증가시키는 메서드
    public void incrementAdViewCount(Long adId) {
        Advertisement advertisement = advertisementRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Advertisement not found"));

        // viewCount가 null일 경우 0으로 초기화
        if (advertisement.getViewCount() == null) {
            advertisement.setViewCount(0L);
        }

        advertisement.incrementViewCount();
        advertisementRepository.save(advertisement);
    }

    // 엔티티를 DTO로 변환
    private AdvertisementDto convertToDto(Advertisement advertisement) {
        AdvertisementDto dto = new AdvertisementDto();
        dto.setId(advertisement.getId());
        dto.setAdContent(advertisement.getAdContent());
        dto.setViewCount(advertisement.getViewCount() != null ? advertisement.getViewCount() : 0L); // Null 체크
        dto.setVideoId(advertisement.getVideo().getId());
        return dto;
    }

    // DTO를 엔티티로 변환
    private Advertisement convertToEntity(AdvertisementDto dto) {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(dto.getId());
        advertisement.setAdContent(dto.getAdContent());
        advertisement.setViewCount(dto.getViewCount() != null ? dto.getViewCount() : 0L); // Null 체크 및 기본값 설정

        // Video 객체 설정, videoId가 없으면 예외 발생
        Video video = videoRepository.findById(dto.getVideoId())
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        advertisement.setVideo(video);

        return advertisement;
    }
}
