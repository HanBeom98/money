package com.sparta.project.Advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    public Advertisement saveAdvertisement(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    public Advertisement findAdvertisementById(Long id) {
        return advertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Advertisement not found"));
    }

    public void incrementAdViewCount(Long adId) {
        Advertisement advertisement = findAdvertisementById(adId);
        advertisement.incrementViewCount();
        advertisementRepository.save(advertisement);
    }
}
