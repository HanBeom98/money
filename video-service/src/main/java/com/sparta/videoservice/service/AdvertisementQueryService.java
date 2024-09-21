package com.sparta.videoservice.service;

import com.sparta.videoservice.model.Advertisement;
import com.sparta.videoservice.repository.AdvertisementReadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 성능 최적화를 위한 읽기 전용 트랜잭션
public class AdvertisementQueryService {

    private final AdvertisementReadRepository advertisementReadRepository;

    public AdvertisementQueryService(AdvertisementReadRepository advertisementReadRepository) {
        this.advertisementReadRepository = advertisementReadRepository;
    }

    public List<Advertisement> getAllAds() {
        return advertisementReadRepository.findAll();
    }

    public Advertisement getAdById(Long adId) {
        return advertisementReadRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
    }
}
