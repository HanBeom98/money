package com.sparta.project.Advertisement;

import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoRepository;
import com.sparta.project.Util.EntityDtoConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final VideoRepository videoRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository, VideoRepository videoRepository) {
        this.advertisementRepository = advertisementRepository;
        this.videoRepository = videoRepository;
    }

    public List<AdvertisementDto> findAllAdvertisements() {
        return advertisementRepository.findAll().stream()
                .map(EntityDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AdvertisementDto saveAdvertisement(AdvertisementDto advertisementDto) {
        Video video = videoRepository.findById(advertisementDto.getVideoId())
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        Advertisement advertisement = EntityDtoConverter.convertToEntity(advertisementDto, video);
        Advertisement savedAd = advertisementRepository.save(advertisement);
        return EntityDtoConverter.convertToDto(savedAd);
    }

    public AdvertisementDto findAdvertisementById(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Advertisement not found"));
        return EntityDtoConverter.convertToDto(advertisement);
    }

    @Transactional
    public void incrementAdViewCount(Long adId) {
        Advertisement advertisement = advertisementRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Advertisement not found"));
        advertisement.incrementViewCount();
        advertisementRepository.save(advertisement);
    }

    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }
}
