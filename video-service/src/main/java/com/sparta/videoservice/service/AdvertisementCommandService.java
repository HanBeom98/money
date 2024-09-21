package com.sparta.videoservice.service;

import com.sparta.videoservice.ExceptionHandler.AdvertisementNotFoundException;
import com.sparta.videoservice.model.Advertisement;
import com.sparta.videoservice.repository.AdvertisementWriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdvertisementCommandService {

    private final AdvertisementWriteRepository advertisementWriteRepository;

    public AdvertisementCommandService(AdvertisementWriteRepository advertisementWriteRepository) {
        this.advertisementWriteRepository = advertisementWriteRepository;
    }

    @Transactional
    public Advertisement createAd(Advertisement ad) {
        return advertisementWriteRepository.save(ad);
    }

    @Transactional
    public void deleteAd(Long adId) {
        Advertisement ad = advertisementWriteRepository.findById(adId)
                .orElseThrow(() -> new AdvertisementNotFoundException("Advertisement not found"));
        advertisementWriteRepository.delete(ad);
    }

    @Transactional
    public Advertisement updateAd(Advertisement ad) {
        return advertisementWriteRepository.save(ad);
    }
}
