package com.sparta.project.Advertisement;

import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AdvertisementService는 광고와 관련된 비즈니스 로직을 처리
 * 광고 생성, 조회, 삭제 및 조회수 증가 등의 기능을 제공
 */
@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final VideoRepository videoRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository, VideoRepository videoRepository) {
        this.advertisementRepository = advertisementRepository;
        this.videoRepository = videoRepository;
    }

    // 모든 광고를 조회하여 DTO 리스트로 반환
    public List<AdvertisementDto> findAllAdvertisements() {
        return advertisementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 광고를 저장하는 메서드, DTO를 엔티티로 변환 후 저장
    @Transactional
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
    @Transactional
    public void incrementAdViewCount(Long adId) {
        Advertisement advertisement = advertisementRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Advertisement not found"));

        advertisement.incrementViewCount();
        advertisementRepository.save(advertisement);
    }

    // 엔티티를 DTO로 변환
    private AdvertisementDto convertToDto(Advertisement advertisement) {
        AdvertisementDto dto = new AdvertisementDto();
        dto.setId(advertisement.getId());
        dto.setAdContent(advertisement.getAdContent());
        dto.setViewCount(advertisement.getViewCount());
        dto.setVideoId(advertisement.getVideo().getId());
        return dto;
    }

    // DTO를 엔티티로 변환
    private Advertisement convertToEntity(AdvertisementDto dto) {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(dto.getId());
        advertisement.setAdContent(dto.getAdContent());
        advertisement.setViewCount(dto.getViewCount() != null ? dto.getViewCount() : 0L);

        Video video = videoRepository.findById(dto.getVideoId())
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        advertisement.setVideo(video);

        return advertisement;
    }

    // 광고를 ID로 삭제하는 메서드
    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }
}
