package com.sparta.project.Advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping
    public List<Advertisement> getAllAdvertisements() {
        return advertisementService.findAllAdvertisements();
    }

    @PostMapping
    public Advertisement createAdvertisement(@RequestBody Advertisement advertisement) {
        return advertisementService.saveAdvertisement(advertisement);
    }

    @GetMapping("/{id}")
    public Advertisement getAdvertisementById(@PathVariable Long id) {
        return advertisementService.findAdvertisementById(id);
    }

    @PostMapping("/{id}/view")
    public void incrementAdViewCount(@PathVariable Long id) {
        advertisementService.incrementAdViewCount(id);  // 광고 시청 횟수 증가
    }
}
